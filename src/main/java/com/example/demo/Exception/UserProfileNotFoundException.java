package com.example.demo.Exception;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException(String message) {
        super(message);
    }
}
