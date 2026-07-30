package com.example.marketservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarketDtoIMPL {
    private Long id;
    private String name;
    private BigDecimal amount;
    private String username;



}
