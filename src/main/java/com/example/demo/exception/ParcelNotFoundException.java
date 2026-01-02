package com.example.demo.exception;

public class ParcelNotFoundException extends RuntimeException{

    public ParcelNotFoundException(String message) {
        super(message);
    }
}
