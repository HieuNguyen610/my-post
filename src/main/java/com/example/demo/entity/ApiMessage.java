package com.example.demo.entity;

import lombok.Getter;

@Getter
public enum ApiMessage {

    PARCEL_MOVED("Parcel moved successfully"),
    PARCEL_NOT_FOUND("Parcel not found"),
    PARCEL_CREATED("Parcel created successfully"),
    PARCEL_ALREADY_DELIVERED("Parcel has already been delivered"),
    PARCEL_BY_ID("Parcel by ID endpoint"),
    PARCEL_UPDATED("Parcel updated successfully"),

    WAREHOUSE_CREATED("Warehouse created successfully"),
    WAREHOUSE_FULL("Warehouse is full"),
    WAREHOUSE_NOT_FOUND("Warehouse not found"),
    WAREHOUSE_BY_ID("Warehouse by ID endpoint"),
    WAREHOUSE_UPDATED("Warehouse updated successfully"),

    USER_CREATED("User created successfully"),
    USER_NOT_FOUND("User not found"),
    INTERNAL_ERROR("Internal server error"),
    USER_BY_ID("User by ID endpoint"),
    USER_UPDATED("User updated successfully")

    ;


    private final String message;

    ApiMessage(String message) {
        this.message = message;
    }

}
