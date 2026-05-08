package com.example.demo.Mapper;

import com.example.demo.Dto.RestaurantDto;
import com.example.demo.Dto.RestaurantDtoIMPL;
import com.example.demo.Entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class})
public interface RestaurantMapper {

    Restaurant toRestaurant(RestaurantDto restaurantDto);

    RestaurantDtoIMPL toRestaurantIMPL(Restaurant restaurant);
}
