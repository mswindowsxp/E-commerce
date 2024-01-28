package com.ecommerce.store.common;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DateConstant {
    DATE("yyyy-MM-dd"),
    DATE_TIME("yyyy-MM-dd hh:mm:ss");
    private final String type;
}
