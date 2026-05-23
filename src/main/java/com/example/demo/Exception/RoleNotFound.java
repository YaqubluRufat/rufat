package com.example.demo.Exception;

public class RoleNotFound extends RuntimeException {
    public RoleNotFound(String message) {
        super(message);
    }
}
