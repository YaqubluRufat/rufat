package com.example.demo.Controller;

import com.example.demo.Dto.OrderDto;
import com.example.demo.Dto.OrderDtoIMPL;
import com.example.demo.Dto.OrderItemDto;
import com.example.demo.Service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @PostMapping("/save")
    public ResponseEntity<OrderDtoIMPL>addOrder(@RequestBody@Valid OrderDto orderDto, Authentication authentication){
        OrderDtoIMPL orderDtoIMPL = orderService.addOrder(orderDto, authentication);
        return ResponseEntity.ok(orderDtoIMPL);
    }
    @GetMapping("/find/my/{id}")
    public ResponseEntity<OrderDtoIMPL> findMyId(@PathVariable Long id,Authentication authentication){
        OrderDtoIMPL myId = orderService.findMyId(id, authentication);
        return ResponseEntity.ok(myId);
    }

    @PutMapping("/update/status")
    public ResponseEntity<OrderDtoIMPL>updateOrder(@PathVariable Long id,@RequestParam String newStatus,Authentication authentication){
        OrderDtoIMPL orderDtoIMPL = orderService.updateStatus(id, newStatus, authentication);
        return ResponseEntity.ok(orderDtoIMPL);
    }
    public ResponseEntity<?>orderDelete(Long id,Authentication authentication){
        orderService.deleteOrder(id,authentication);
        return ResponseEntity.ok("Deleted successfully");
    }
}
