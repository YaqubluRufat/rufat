package com.example.demo.Dto;

import com.example.demo.Entity.Restaurant;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDto {
    @NotBlank(message = "cannot be empty")
    @Min(value = 2,message = "Must be at least 2 characters long")
    @Max(value = 15,message = "Must be at most 15 characters")
    private String name;
    @NotNull(message = "cannot be empty")
    @Positive
    private BigDecimal price;
    @NotNull(message = "cannot be empty")
    private Long restaurantId;
}
