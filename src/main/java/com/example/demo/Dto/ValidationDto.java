package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Named;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationDto {

    private String message;
    private String field;
    private Object rejectedValue;

}
