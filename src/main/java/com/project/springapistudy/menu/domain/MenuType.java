package com.project.springapistudy.menu.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum MenuType {
    BEVERAGE,
    DESSERT;

    @JsonCreator
    public static MenuType from(String input) {
        return Arrays.stream(MenuType.values())
                .filter(menuType -> menuType.name().equals(input))
                .findFirst()
                .orElse(null);
    }
}
