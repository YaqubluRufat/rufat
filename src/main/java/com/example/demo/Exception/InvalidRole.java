package com.example.demo.Exception;

public class InvalidRole extends RuntimeException {
    public InvalidRole(String message) {
        super(message);
    }
}
