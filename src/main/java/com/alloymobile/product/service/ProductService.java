package com.alloymobile.product.service;

import com.alloymobile.product.integration.client.model.Address;
import com.alloymobile.product.integration.client.model.AddressType;
import com.alloymobile.product.persistence.model.Product;
import com.alloymobile.product.persistence.model.Location;
import com.alloymobile.product.persistence.repository.ProductRepository;
import com.alloymobile.product.integration.client.ClientServiceCaller;
import com.querydsl.core.types.Predicate;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ClientServiceCaller clientServiceCaller;

    public ProductService(ProductRepository productRepository, ClientServiceCaller clientServiceCaller) {
        this.productRepository = productRepository;
        this.clientServiceCaller = clientServiceCaller;
    }

    public Mono<Page<Product>> findAllProduct(Predicate predicate, Pageable pageable){
        return this.productRepository.count()
                .flatMap(productCount -> {
                    return this.productRepository.findAll(predicate,pageable.getSort())
                            .buffer(pageable.getPageSize(),(pageable.getPageNumber() + 1))
                            .elementAt(pageable.getPageNumber(), new ArrayList<>())
                            .map(products -> new PageImpl<>(products, pageable, productCount));
                });
    }

    public Mono<Page<Product>> findAllSaleProduct(Double lat,Double lon, Integer distance,Pageable pageable){
        Location location = new Location();
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(lat);
        coordinates.add(lon);
        location.setType("Point");
        location.setCoordinates(coordinates);
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        return this.productRepository.count()
                .flatMap(productCount -> {
                    return this.productRepository.getProductByLocation(location,distance,zonedDateTime,pageable.getSort())
                            .buffer(pageable.getPageSize(),(pageable.getPageNumber() + 1))
                            .elementAt(pageable.getPageNumber(), new ArrayList<>())
                            .map(products -> new PageImpl<>(products, pageable, productCount));
                });
    }

    public Mono<Product> findProductById(String id){
        return productRepository.findById(id);
    }

    public Mono<Product> addProduct(Product product, String token){
        product.setId(new ObjectId().toString());
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        product.setExpiryDate(now);
        return this.clientServiceCaller.getClientById(product.getClientId(),token).flatMap(c->{
           Optional<Address> address = c.getAddresses().stream().filter(a->a.getType().equals(AddressType.STORE)).findFirst();
           if(address.isPresent()){
               product.setAddress(this.clientServiceCaller.clientMapper.toAddress(address.get()));
               product.setLocation(this.clientServiceCaller.clientMapper.toLocation(address.get()));
           }
           return productRepository.save(product);
        });
    }

    public Mono<Product> updateProduct(String id, Product product){
        return productRepository.findById(id).flatMap(f->{
            product.setId(f.getId());
            return this.productRepository.save(product);
        });
    }

    public Mono<Void> deleteProduct(String id){
        return this.productRepository.deleteById(id);
    }
}
