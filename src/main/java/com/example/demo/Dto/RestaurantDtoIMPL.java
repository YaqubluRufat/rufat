package com.example.demo.Dto;

import com.example.demo.Entity.MenuItem;
import com.example.demo.Entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDtoIMPL {
    private Long id;
    private String name;
    private String location;
    private String telephonePhone;
    private List<MenuItemDtoIMPL> menuItem;
    private List<ReviewDtoIMPL> review;
}
