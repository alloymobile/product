package com.alloymobiletech.product.servicecaller;

import com.alloymobiletech.product.config.ProductProperties;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseServiceCaller {

    @Autowired
    protected ProductProperties productProperties;
}
