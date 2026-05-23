package com.example.demo.Exception;

public class PermissionAlreadyExists extends RuntimeException {
    public PermissionAlreadyExists(String message) {
        super(message);
    }
}
