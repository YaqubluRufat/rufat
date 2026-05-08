package com.example.demo.Dto;

import com.example.demo.Entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDtoIMPL {
    private Long id;
    private RestaurantDtoIMPL restaurant;
    private Long rating;
    private String commentaries;
}
