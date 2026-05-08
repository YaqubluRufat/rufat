package com.example.demo.Exception;

public class InvalidSort extends RuntimeException {
    public InvalidSort(String message) {
        super(message);
    }
}
