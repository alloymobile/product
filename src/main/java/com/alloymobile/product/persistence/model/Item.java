package com.alloymobile.product.persistence.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Item {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private String imageUrl;
    private String description;
    private Integer quantity;
    private String ingredients;
    private String allergies;
    private ZonedDateTime expiryDate;
}
