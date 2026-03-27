package com.example.demo.DTO;

import com.example.demo.Entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeDtoIMPL {
    private Long Id;
    private String name;

    private List<ProductDtoIMPL> products;
}
