package com.example.demo.Dto;

import com.example.demo.Entity.MenuItem;
import com.example.demo.Entity.Order;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDtoIMPL {
    private MenuItemDtoIMPL menuItem;
    private Long say;


}
