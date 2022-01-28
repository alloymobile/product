package com.alloymobile.product.resource;

import com.alloymobile.product.model.Product;
import com.alloymobile.product.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductResource {

    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

//    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value="/v1/products", produces = "application/json")
    public Flux<Product> getAllProduct(){
        return this.productService.findAllProduct();
    }

    @GetMapping(value="/products/sale", produces = "application/json")
    public Flux<Product> getProductForSale(){
        return this.productService.findProductForSale();
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/api/v1/products/{id}", produces = "application/json")
    public Mono<Product> getProductById(@PathVariable(name="id") String id){
        return this.productService.findProductById(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/api/v1/products", produces = "application/json")
    public Mono<Product> addProduct(@RequestBody Product product){
        return this.productService.addProduct(product);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/api/v1/products/{id}")
    public Mono<Product> updateProduct(@PathVariable(name="id") String id, @RequestBody Product product){
        return this.productService.updateProduct(id, product);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = "/api/v1/products/{id}")
    public Mono<Void> deleteProduct(@PathVariable(name="id") String id){
        return this.productService.deleteProduct(id);
    }
}
