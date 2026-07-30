package com.example.productservice.Service;

import com.example.productservice.DTO.MarketDto;
import com.example.productservice.Entity.Product;
import com.example.productservice.Repository.ProductRepository;
import com.example.productservice.Security.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ProductSecurity {
    private final MarketClient marketClient;
    private final ProductRepository productRepository;

    public ProductSecurity(MarketClient marketClient, ProductRepository productRepository) {
        this.marketClient = marketClient;
        this.productRepository = productRepository;
    }

    public boolean isOwner(Long marketId, Authentication authentication) {

        if(!(authentication.getPrincipal() instanceof MyUserDetails user)){
            return false;
        }
        return marketClient.extractByIdAndUsername(marketId,user.getUsername());

    }
    public boolean isUser(Long id,Authentication authentication){
        if(!(authentication.getPrincipal() instanceof MyUserDetails user)){
            return false;
        }
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        MarketDto market = marketClient.findById(product.getMarketId());
        return market.getUsername().equals(user.getUsername());
    }
}
