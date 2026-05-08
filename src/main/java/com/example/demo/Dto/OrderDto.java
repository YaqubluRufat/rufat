package com.example.demo.Dto;

import com.example.demo.Entity.Restaurant;
import com.example.demo.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    @NotNull(message = "cannot be empty")
    private Long restaurantId;

}
