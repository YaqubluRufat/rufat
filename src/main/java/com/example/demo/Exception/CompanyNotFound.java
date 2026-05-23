package com.example.demo.Exception;

public class CompanyNotFound extends RuntimeException {
    public CompanyNotFound(String message) {
        super(message);
    }
}
