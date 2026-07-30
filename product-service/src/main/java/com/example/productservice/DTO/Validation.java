package com.example.productservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Validation {
    private String message;
    private String field;
    private Object rejectedValue;
}
