package com.alloymobile.product.servicecaller;

import com.alloymobile.product.config.ProductProperties;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseServiceCaller {

    @Autowired
    protected ProductProperties productProperties;
}
