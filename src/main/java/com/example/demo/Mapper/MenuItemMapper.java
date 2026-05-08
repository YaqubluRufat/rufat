package com.example.demo.Mapper;

import com.example.demo.Dto.MenuItemDto;
import com.example.demo.Dto.MenuItemDtoIMPL;
import com.example.demo.Entity.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    @Mapping(source = "restaurantId", target = "restaurant.id")
    MenuItem toMenuItem(MenuItemDto menuItemDto);

    @Mapping(source = "restaurant.id", target = "restaurantId")
    MenuItemDtoIMPL toMenuItemDtoIMPL(MenuItem menuItem);
}
