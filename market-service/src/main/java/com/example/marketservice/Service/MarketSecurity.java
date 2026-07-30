package com.example.marketservice.Service;

import com.example.marketservice.Repository.MarketRepository;
import com.example.marketservice.Security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class MarketSecurity {

    private final MarketRepository orderRepository;


    public MarketSecurity(MarketRepository orderRepository) {
        this.orderRepository = orderRepository;

    }

    public boolean isOwner(Long id, Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof MyUserDetails user)) {
            return false;
        }
        return orderRepository.existsByIdAndUsername(id,user.getUsername());

    }
}

