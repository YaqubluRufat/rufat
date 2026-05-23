package com.example.demo.Exception;

public class GamesNotFoundException extends RuntimeException {
    public GamesNotFoundException(String message) {
        super(message);
    }
}
