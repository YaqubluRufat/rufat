package com.example.demo.DTO;

import com.example.demo.Entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeDto {
  @NotBlank(message = "cannot be empty")
    private String name;
  @NotNull(message = "cannot be empty")
    private List<Long> productsId;
}
