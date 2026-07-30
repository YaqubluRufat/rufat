package com.example.ordservice.Controller;

import com.example.ordservice.Dto.OrderDto;
import com.example.ordservice.Dto.OrderDtoIMPL;
import com.example.ordservice.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/save")
    public ResponseEntity<OrderDtoIMPL>addOrder(@RequestBody OrderDto orderDto){
        OrderDtoIMPL orderDtoIMPL = orderService.addOrder(orderDto);
        return ResponseEntity.ok(orderDtoIMPL);
    }
}
