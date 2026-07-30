package com.example.productservice.Exception;

public class MarketNotFound extends RuntimeException {
    public MarketNotFound(String message) {
        super(message);
    }
}
