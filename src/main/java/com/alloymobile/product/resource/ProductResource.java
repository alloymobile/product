package com.alloymobile.product.resource;

import com.alloymobile.product.application.config.Constant;
import com.alloymobile.product.application.utils.PageData;
import com.alloymobile.product.persistence.model.Product;
import com.alloymobile.product.service.product.ProductBinding;
import com.alloymobile.product.service.product.ProductService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
public class ProductResource {

    private final ProductService productService;

    private final PageData page;

    public ProductResource(ProductService productService, PageData page) {
        this.productService = productService;
        this.page = page;
    }

    @GetMapping(value= Constant.FREE_URL+"/products", produces = "application/json")
    public Mono<Page<Product>> getAllProduct(@QuerydslPredicate(root = Product.class,bindings = ProductBinding.class) Predicate predicate
            , @RequestParam(name = "search",required = false) String search
            , @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
            , @RequestParam(value = "sort", required = false) String sort) {
        if (Objects.nonNull(search)) {
            predicate = ProductBinding.createSearchQuery(search);
        }
        return this.productService.findAllProduct(predicate, this.page.getPage(page, size, sort));
    }

    @GetMapping(value=Constant.FREE_URL+"/products/sale", produces = "application/json")
    public Mono<Page<Product>> getAllSaleProduct(
            @RequestParam(value = "lat") Double lat,
            @RequestParam(value = "lon") Double lon,
            @RequestParam(value = "distance") Integer distance,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", required = false) String sort){
        return this.productService.findAllSaleProduct(lat,lon,distance,this.page.getPage(page,size,sort));
    }


    @GetMapping(value=Constant.FREE_URL+"/products/{id}", produces = "application/json")
    public Mono<Product> getProductById(@PathVariable(name="id") String id){
        return this.productService.findProductById(id);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value=Constant.BASE_URL+"/products", produces = "application/json")
    public Mono<Product> addProduct(@RequestBody Product product, @RequestHeader(value= HttpHeaders.AUTHORIZATION) String token){
        return this.productService.addProduct(product,token);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value=Constant.BASE_URL+"/products/{id}")
    public Mono<Product> updateProduct(@PathVariable(name="id") String id, @RequestBody Product product){
        return this.productService.updateProduct(id, product);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value=Constant.BASE_URL+"/products/{id}")
    public Mono<Void> deleteProduct(@PathVariable(name="id") String id){
        return this.productService.deleteProduct(id);
    }
}
