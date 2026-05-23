package com.example.demo.Exception;

public class MarketNotFound extends RuntimeException {
    public MarketNotFound(String message) {
        super(message);
    }
}
