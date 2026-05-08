package com.example.demo.Exception;

public class MenuItemNotFound extends RuntimeException {
    public MenuItemNotFound(String message) {
        super(message);
    }
}
