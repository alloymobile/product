package com.alloymobiletech.product.model;

import lombok.Data;

import java.util.List;

@Data
public class Location {
    private String type;
    private List<Double> coordinates;
}
