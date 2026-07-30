package com.example.ordservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketDtoIMPL {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String username;
}
