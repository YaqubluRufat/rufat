package com.example.demo.DTO;

import com.example.demo.Entity.Currency;
import com.example.demo.Entity.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoIMPL {

    private Long Id;
    private String name;
    private Integer price;
    private String color;
    private Currency currency;


}
