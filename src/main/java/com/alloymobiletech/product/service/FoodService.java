package com.alloymobiletech.product.service;

import com.alloymobiletech.product.model.Food;
import com.alloymobiletech.product.model.Location;
import com.alloymobiletech.product.repository.FoodRepository;
import com.alloymobiletech.product.servicecaller.client.ClientServiceCaller;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    private final ClientServiceCaller clientServiceCaller;

    public FoodService(FoodRepository foodRepository, ClientServiceCaller clientServiceCaller) {
        this.foodRepository = foodRepository;
        this.clientServiceCaller = clientServiceCaller;
    }

    public Flux<Food> findAllFood(){
//        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        return foodRepository.findAll();
    }

    public Flux<Food> findFoodForSale(){
        Location location = new Location();
        List<Double> points = new ArrayList<>();
        points.add(43.2205222);
        points.add(-79.8434827);
        location.setType("Point");
        location.setCoordinates(points);
        return foodRepository.getFoodByLocation(location,100);
    }

    public Mono<Food> findFoodById(String id){
        return foodRepository.findById(id);
    }

    public Mono<Food> addFood(Food food){
        food.setId(new ObjectId().toString());
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        food.setExpiryDate(now);
//        return this.clientServiceCaller.getClientById(food.getClientId()).flatMap(c->{
//           c.getAddresses().stream().filter(a->a.getType().equals(AddressType.STORE)).findFirst()
//               .ifPresent(sa->{
//                   food.setAddress(sa);
//               });
           return foodRepository.save(food);
//        });
    }

    public Mono<Food> updateFood(String id, Food food){
        return foodRepository.findById(id).flatMap(f->{
            food.setId(f.getId());
            return this.foodRepository.save(food);
        });
    }

    public Mono<Void> deleteFood(String id){
        return this.foodRepository.deleteById(id);
    }
}
