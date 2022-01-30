package com.alloymobile.product.persistence.repository;

import com.alloymobile.product.persistence.model.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String>, ReactiveQuerydslPredicateExecutor<Order> {
}
