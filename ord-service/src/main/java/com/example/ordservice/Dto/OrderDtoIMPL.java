package com.example.ordservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoIMPL {

    private Long id;
    private Long marketId;
    private Long productId;
}
