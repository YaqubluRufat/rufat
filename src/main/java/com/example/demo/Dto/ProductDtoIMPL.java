package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDtoIMPL {
    private Long id;
    private String name;
    private BigDecimal price;
    private LocalDateTime createdData;
    private LocalDateTime updatedDate;

}
