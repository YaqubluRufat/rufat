package com.example.marketservice.Exception;

public class MarketNotFound extends RuntimeException {
    public MarketNotFound(String message) {
        super(message);
    }
}
