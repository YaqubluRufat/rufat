package com.example.demo.Dto;

import com.example.demo.Entity.OrderItem;
import com.example.demo.Entity.Restaurant;
import com.example.demo.Entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDtoIMPL {
    private Long id;
    private RestaurantDtoIMPL restaurant;
    private BigDecimal totalPrice;
    private List<Long> orderItemIds;
    private String status;

}
