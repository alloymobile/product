package com.alloymobile.product.repository;

import com.alloymobile.product.model.Location;
import com.alloymobile.product.model.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.ZonedDateTime;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

    Flux<Product> findAllByExpiryDateAfter(ZonedDateTime currentDate);

    @Query("{'name' : ?0}")
    Flux<Product> getProductByName(String name);

    @Query("{'location' : {$near : { $geometry : ?0 , $maxDistance: ?1 } }  }")
    Flux<Product> getProductByLocation(Location location, Integer distance);
}
