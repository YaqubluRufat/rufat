package com.example.demo.Dto;

import com.example.demo.Entity.MenuItem;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.Status;
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
public class OrderItemDto {
    @NotNull(message = "cannot be empty")
    private Long menuItemId;
    @NotNull(message = "cannot be empty")
    private Long say;
    @NotNull(message = "cannot be empty")
    private Long orderId;
}
