package com.alloymobiletech.product.repository;

import com.alloymobiletech.product.model.Food;
import com.alloymobiletech.product.model.Location;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.ZonedDateTime;

@Repository
public interface FoodRepository extends ReactiveMongoRepository<Food, String> {

    Flux<Food> findAllByExpiryDateAfter(ZonedDateTime currentDate);

    @Query("{'name' : ?0}")
    Flux<Food> getFoodByName(String name);

    @Query("{'location' : {$near : { $geometry : ?0 , $maxDistance: ?1 } }  }")
    Flux<Food> getFoodByLocation(Location location,Integer distance);
}
