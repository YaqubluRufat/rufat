package com.example.demo.Dto;

import com.example.demo.Entity.Market;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDto {
    private String name;
    private BigDecimal price;
    private Long marketId;
}
