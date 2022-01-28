package com.alloymobiletech.product.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Document(collection  = "foods")
@Data
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Food  implements Serializable {
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
}
