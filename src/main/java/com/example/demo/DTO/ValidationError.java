package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {
    private String message;
    private int statusCode;
    private LocalDateTime timestamp;
    private String path;
}
