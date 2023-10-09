package com.project.springapistudy.common.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.project.springapistudy.product.domain.ProductType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Flag {
    Y,
    N
    ;

    @JsonCreator
    public static Flag from(String input) {
        return Arrays.stream(Flag.values())
                .filter(flag -> flag.name().equals(input))
                .findFirst()
                .orElse(null);
    }

    public boolean isY() {
        return this == Flag.Y;
    }
}
