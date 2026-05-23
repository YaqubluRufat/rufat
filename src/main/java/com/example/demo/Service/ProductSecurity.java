package com.example.demo.Service;

import com.example.demo.Entity.Market;
import com.example.demo.Exception.MarketNotFound;
import com.example.demo.Repository.MarketRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class ProductSecurity {
    private final MarketRepository marketRepository;
    private final ProductRepository productRepository;

    public ProductSecurity(MarketRepository marketRepository, ProductRepository productRepository) {
        this.marketRepository = marketRepository;
        this.productRepository = productRepository;
    }

    public boolean isOwner(Long marketId, Authentication authentication) {

        if(!(authentication.getPrincipal() instanceof MyUserDetails myUserDetails)){
            return false;
    }

        return marketRepository.existsByIdAndUserId(marketId,myUserDetails.getId());

    }


    public boolean owner(Long id,Authentication authentication){
        if(!(authentication.getPrincipal() instanceof MyUserDetails user)){
            return false;
        }
       return productRepository.existsByIdAndMarketUserId(id, user.getId());


    }

}
