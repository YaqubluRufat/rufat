package com.example.demo.Exception;

public class ProductTypeNotFoundException extends RuntimeException {
    public ProductTypeNotFoundException(String message) {
        super(message);
    }
}
