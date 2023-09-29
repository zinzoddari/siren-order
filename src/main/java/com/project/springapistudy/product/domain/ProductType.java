package com.project.springapistudy.product.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum ProductType {
    BEVERAGE,
    DESSERT;

    @JsonCreator
    public static ProductType from(String input) {
        return Arrays.stream(ProductType.values())
                .filter(productType -> productType.name().equals(input))
                .findFirst()
                .orElse(null);
    }
}
