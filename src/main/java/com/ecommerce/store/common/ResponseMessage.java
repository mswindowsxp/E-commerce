package com.ecommerce.store.common;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    JWT_INVALID_SIG(500, "Invalid JWT signature"),
    JWT_INVALID_TOKEN(500, "Invalid JWT token"),
    JWT_EXPIRED(500, "Expired JWT token"),
    JWT_UNSUPPORTED(500, "Unsupported JWT token"),
    JWT_EMPTY(500, "JWT claims string is empty"),
    AUTH_ROLE_NOT_FIND(500, "Fail! -> Cause: User Role not find"),
    AUTH_WRONG_INFORMATION(500, "Wrong username or password"),
    SUCCESS(200, "Success"),
    NOT_FOUND(204, "Not Found"),
    ERROR_SYSTEM(500, "Error while processing");

    public final int statusCode;
    public final String message;


}

