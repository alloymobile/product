package com.alloymobile.product.persistence.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Document(collection  = "orders")
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Order {
    @Id
    private String id;
    private String client;
    private String seller;
    private ZonedDateTime orderDate;
    private BigDecimal total;
    private BigDecimal discount;
    private BigDecimal tax;
    private List<Item> items;
    private OrderStatus status;
}
