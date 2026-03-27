package com.example.demo.DTO;

import com.example.demo.Entity.Currency;
import com.example.demo.Entity.ProductType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    @NotBlank(message = "cannot be empty")
    private String name;
    @Positive
    @NotNull(message = "cannot be empty")
    private Integer price;
    @NotBlank(message = "cannot be empty")
    private String color;
    private Currency currency;


}
