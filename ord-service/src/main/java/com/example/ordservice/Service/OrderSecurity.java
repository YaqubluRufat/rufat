package com.example.ordservice.Service;

import com.example.ordservice.Repository.OrderRepository;
import com.example.ordservice.Security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class OrderSecurity {

    private final OrderRepository orderRepository;

    public OrderSecurity(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public boolean isUser(Long id, Authentication authentication){
        if(!(authentication.getPrincipal() instanceof MyUserDetails user)){
            return false;
        }
        return orderRepository.existsByIdAndUserId(id,user.getId());
    }
}
