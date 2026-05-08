package com.example.demo.Mapper;

import com.example.demo.Dto.OrderItemDto;
import com.example.demo.Dto.OrderItemDtoIMPL;
import com.example.demo.Entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {MenuItemMapper.class})
public interface OrderItemMapper {
    @Mapping(source = "menuItemId", target = "menuItem.id")
    @Mapping(source = "orderId", target = "order.id")
    OrderItem toOrderItem(OrderItemDto orderItemDto);

    OrderItemDtoIMPL toOrderItemDtoIMPL(OrderItem orderItem);
}
