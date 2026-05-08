package com.example.demo.Exception;

public class RestaurantNotFound extends RuntimeException {
    public RestaurantNotFound(String message) {
        super(message);
    }
}
