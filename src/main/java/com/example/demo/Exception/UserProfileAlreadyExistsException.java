package com.example.demo.Exception;

public class UserProfileAlreadyExistsException extends RuntimeException {
    public UserProfileAlreadyExistsException(String message) {
        super(message);
    }
}
