package com.alloymobile.product.persistence.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Document(collection  = "products")
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Product implements Serializable {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private String description;
    private Integer quantity;
    private String ingredients;
    private String allergies;
    private String clientId;
    private ZonedDateTime expiryDate;
    private Address address;
    private Location location;
}
