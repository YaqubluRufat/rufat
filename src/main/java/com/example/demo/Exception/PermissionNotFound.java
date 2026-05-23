package com.example.demo.Exception;

public class PermissionNotFound extends RuntimeException {
    public PermissionNotFound(String message) {
        super(message);
    }
}
