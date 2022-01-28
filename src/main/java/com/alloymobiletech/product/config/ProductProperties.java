package com.alloymobiletech.product.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties("product")
public class ProductProperties {
    private String clientServiceUrl;
    private String token;
}
