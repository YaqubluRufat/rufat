package com.example.demo.Controller;

import com.example.demo.Dto.OrderItemDto;
import com.example.demo.Dto.OrderItemDtoIMPL;
import com.example.demo.Service.OrderItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/item")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("/save")
    public ResponseEntity<OrderItemDtoIMPL> addOrderItem(@RequestBody@Valid OrderItemDto orderItemDto, Authentication authentication) {
        OrderItemDtoIMPL orderItemDtoIMPL = orderItemService.addOrderItem(orderItemDto, authentication);
        return ResponseEntity.ok(orderItemDtoIMPL);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteOrderItem(@PathVariable Long id,Authentication authentication){
        orderItemService.deleteOrderItemDto(id,authentication);
        return ResponseEntity.ok("Deleted successfully");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<OrderItemDtoIMPL>updateOrderItem(@PathVariable Long id, @RequestBody @Valid OrderItemDto orderItemDto, Authentication authentication){
        OrderItemDtoIMPL orderItemDtoIMPL = orderItemService.updateOrderDtoIMPL(id, authentication, orderItemDto);
        return ResponseEntity.ok(orderItemDtoIMPL);
    }
}

