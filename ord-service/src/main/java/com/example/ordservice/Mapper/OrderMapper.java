package com.example.ordservice.Mapper;

import com.example.ordservice.Dto.OrderDto;
import com.example.ordservice.Dto.OrderDtoIMPL;
import com.example.ordservice.Entity.Order;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder (OrderDto orderDto);

    OrderDtoIMPL toDtoIMPL (Order order);
}
