package com.alloymobile.product.service;

import com.alloymobile.product.model.Product;
import com.alloymobile.product.model.Location;
import com.alloymobile.product.repository.ProductRepository;
import com.alloymobile.product.servicecaller.client.ClientServiceCaller;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ClientServiceCaller clientServiceCaller;

    public ProductService(ProductRepository productRepository, ClientServiceCaller clientServiceCaller) {
        this.productRepository = productRepository;
        this.clientServiceCaller = clientServiceCaller;
    }

    public Flux<Product> findAllProduct(){
        return productRepository.findAll();
    }

    public Flux<Product> findProductForSale(){
        Location location = new Location();
        List<Double> points = new ArrayList<>();
        points.add(43.2205222);
        points.add(-79.8434827);
        location.setType("Point");
        location.setCoordinates(points);
        return productRepository.getProductByLocation(location,100);
    }

    public Mono<Product> findProductById(String id){
        return productRepository.findById(id);
    }

    public Mono<Product> addProduct(Product product){
        product.setId(new ObjectId().toString());
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        product.setExpiryDate(now);
//        return this.clientServiceCaller.getClientById(product.getClientId()).flatMap(c->{
//           c.getAddresses().stream().filter(a->a.getType().equals(AddressType.STORE)).findFirst()
//               .ifPresent(sa->{
//                   product.setAddress(sa);
//               });
           return productRepository.save(product);
//        });
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
