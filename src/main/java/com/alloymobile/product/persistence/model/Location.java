package com.alloymobile.product.persistence.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Location  implements Serializable {
    private String type;
    private List<Double> coordinates;
}
