package com.alloymobile.product.persistence.repository;

import com.alloymobile.product.persistence.model.Location;
import com.alloymobile.product.persistence.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.ZonedDateTime;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>, ReactiveQuerydslPredicateExecutor<Product> {

    @Query("{$and:[{'location' : {$near : { $geometry : ?0 , $maxDistance: ?1 } } },{'expiryDate' : {$gte : ?2 } }]}")
    Flux<Product> findAllSaleProduct(Location location, Integer distance, ZonedDateTime zonedDateTime, Sort sort);

}
