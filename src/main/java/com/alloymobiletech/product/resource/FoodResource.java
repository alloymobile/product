package com.alloymobiletech.product.resource;

import com.alloymobiletech.product.model.Food;
import com.alloymobiletech.product.service.FoodService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FoodResource {

    private final FoodService foodService;

    public FoodResource(FoodService foodService) {
        this.foodService = foodService;
    }

//    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value="/v1/foods", produces = "application/json")
    public Flux<Food> getAllFood(){
        return this.foodService.findAllFood();
    }

    @GetMapping(value="/foods/sale", produces = "application/json")
    public Flux<Food> getFoodForSale(){
        return this.foodService.findFoodForSale();
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/api/v1/foods/{id}", produces = "application/json")
    public Mono<Food> getFoodById(@PathVariable(name="id") String id){
        return this.foodService.findFoodById(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = "/api/v1/foods", produces = "application/json")
    public Mono<Food> addFood(@RequestBody Food food){
        return this.foodService.addFood(food);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/api/v1/foods/{id}")
    public Mono<Food> updateFood(@PathVariable(name="id") String id, @RequestBody Food food){
        return this.foodService.updateFood(id,food);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = "/api/v1/foods/{id}")
    public Mono<Void> deleteFood(@PathVariable(name="id") String id){
        return this.foodService.deleteFood(id);
    }
}
