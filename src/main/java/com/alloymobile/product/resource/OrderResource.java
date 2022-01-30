package com.alloymobile.product.resource;

import com.alloymobile.product.application.config.Constant;
import com.alloymobile.product.application.utils.PageData;
import com.alloymobile.product.persistence.model.Order;
import com.alloymobile.product.service.order.OrderBinding;
import com.alloymobile.product.service.order.OrderService;
import com.querydsl.core.types.Predicate;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;

@RestController
public class OrderResource {

    private final OrderService orderService;

    private final PageData page;

    public OrderResource(OrderService orderService, PageData page) {
        this.orderService = orderService;
        this.page = page;
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value= Constant.BASE_URL+"/orders", produces = "application/json")
    public Mono<Page<Order>> getAllOrder(@QuerydslPredicate(root = Order.class,bindings = OrderBinding.class) Predicate predicate
            , @RequestParam(name = "search",required = false) String search
            , @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "size", required = false) Integer size
            , @RequestParam(value = "sort", required = false) String sort){
        if(Objects.nonNull(search)){
            predicate = OrderBinding.createSearchQuery(search);
        }
        return this.orderService.findAllOrder(predicate,this.page.getPage(page, size, sort));
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/api/v1/orders/{id}", produces = "application/json")
    public Mono<Order> getOrderById(@PathVariable(name="id") String id){
        return this.orderService.findOrderById(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(value = Constant.BASE_URL+"/orders",  consumes = "application/json", produces = "application/json")
    public Mono<Order> addOrder(@RequestBody Order order,@RequestHeader(value= HttpHeaders.AUTHORIZATION) String token){
        return this.orderService.addOrder(order,token);
    }

//    @SecurityRequirement(name = "bearerAuth")
//    @PostMapping( value= Constant.BASE_URL+"/orders/all", consumes = "application/json", produces = "application/json")
//    public void addOrders(@RequestBody Order[] orders, @RequestHeader(value= HttpHeaders.AUTHORIZATION) String token){
//        return this.orderService.addOrders(Arrays.asList(orders),token);
//    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/api/v1/orders/{id}")
    public Mono<Order> updateOrder(@PathVariable(name="id") String id, @RequestBody Order order){
        return this.orderService.updateOrder(id, order);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping(value = "/api/v1/orders/{id}")
    public Mono<Void> deleteOrder(@PathVariable(name="id") String id){
        return this.orderService.deleteOrder(id);
    }
}
