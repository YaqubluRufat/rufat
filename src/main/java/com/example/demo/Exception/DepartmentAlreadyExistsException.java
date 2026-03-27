package com.example.demo.Exception;

public class DepartmentAlreadyExistsException extends RuntimeException {
    public DepartmentAlreadyExistsException(String message) {
        super(message);
    }
}
