package com.alloymobile.product.service.order;

import com.alloymobile.product.integration.client.ClientServiceCaller;
import com.alloymobile.product.persistence.model.Order;
import com.alloymobile.product.persistence.model.OrderStatus;
import com.alloymobile.product.persistence.repository.OrderRepository;
import com.alloymobile.product.service.product.ProductService;
import com.querydsl.core.types.Predicate;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final ClientServiceCaller clientServiceCaller;

    private final ProductService productService;

    public OrderService(OrderRepository orderRepository, ClientServiceCaller clientServiceCaller, ProductService productService) {
        this.orderRepository = orderRepository;
        this.clientServiceCaller = clientServiceCaller;
        this.productService = productService;
    }

    public Mono<Page<Order>> findAllOrder(Predicate predicate, Pageable pageable){
        return this.orderRepository.count()
                .flatMap(orderCount -> {
                    return this.orderRepository.findAll(predicate,pageable.getSort())
                            .buffer(pageable.getPageSize(),(pageable.getPageNumber() + 1))
                            .elementAt(pageable.getPageNumber(), new ArrayList<>())
                            .map(orders -> new PageImpl<>(orders, pageable, orderCount));
                });
    }

    public Mono<Order> findOrderById(String id){
        return orderRepository.findById(id);
    }

    public Mono<Order> addOrder(Order order, String token) {
        return this.clientServiceCaller.getClientById(order.getClient(), token).flatMap(c -> {
            Flux.fromIterable(order.getItems()).flatMap(o->{
                this.productService.findProductById(o.getId()).flatMap(p->{
                    if(p.getQuantity() > o.getQuantity()){
                        Integer quantity = p.getQuantity() - o.getQuantity();
                        p.setQuantity(quantity);
                        this.productService.updateProduct(p.getId(),p).subscribe();
                    }
                    return Mono.empty();
                }).subscribe();
                return Flux.empty();
            }).subscribe();
            order.setId(new ObjectId().toString());
            order.setStatus(OrderStatus.CART);
            return orderRepository.save(order);
        });
    }

    public Mono<Order> updateOrder(String id, Order order){
        return orderRepository.findById(id).flatMap(f->{
            order.setId(f.getId());
            return this.orderRepository.save(order);
        });
    }

    public Mono<Void> deleteOrder(String id){
        return this.orderRepository.deleteById(id);
    }
}
