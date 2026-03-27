package com.example.demo.Exception;

public class ProductTypeAlreadyExistsException extends RuntimeException {
    public ProductTypeAlreadyExistsException(String message) {
        super(message);
    }
}
