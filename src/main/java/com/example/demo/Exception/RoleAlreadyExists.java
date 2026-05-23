package com.example.demo.Exception;

public class RoleAlreadyExists extends RuntimeException {
    public RoleAlreadyExists(String message) {
        super(message);
    }
}
