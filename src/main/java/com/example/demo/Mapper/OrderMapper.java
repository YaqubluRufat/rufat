package com.example.demo.Mapper;

import com.example.demo.Dto.OrderDto;
import com.example.demo.Dto.OrderDtoIMPL;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface OrderMapper {
    @Mapping(source = "restaurantId", target = "restaurant.id")
    Order toOrder(OrderDto orderDto);

    @Mapping(source = "orderItem", target = "orderItemIds", qualifiedByName = "toMapLong")
    OrderDtoIMPL toOrderDtoIMPL(Order order);

    @Named("toMapLong")
    default List<Long> toMapLong(List<OrderItem> ids) {
        if (ids == null) return null;
        return ids.stream().map(OrderItem::getId).toList();
    }
}
