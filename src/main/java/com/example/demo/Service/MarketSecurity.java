package com.example.demo.Service;

import com.example.demo.Repository.MarketRepository;
import com.example.demo.Security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class MarketSecurity {
    private final MarketRepository marketRepository;

    public MarketSecurity(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }
    public boolean isOwner(Long marketId, Authentication authentication){
        if(!(authentication.getPrincipal() instanceof MyUserDetails user)){
            return false;
        } return marketRepository.existsByIdAndUserId(marketId, user.getId());
    }
}
