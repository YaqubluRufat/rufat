package com.example.marketservice.Service;

import com.example.marketservice.DTO.MarketDto;
import com.example.marketservice.DTO.MarketDtoIMPL;
import com.example.marketservice.Entity.Market;

import com.example.marketservice.Exception.MarketNotFound;
import com.example.marketservice.Mapper.MarketMapper;
import com.example.marketservice.Repository.MarketRepository;
import com.example.marketservice.Security.MyUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class MarketService {
    private final MarketMapper marketMapper;
    private final MarketRepository orderRepository;

    public MarketService(MarketMapper marketMapper, MarketRepository orderRepository) {
        this.marketMapper = marketMapper;
        this.orderRepository = orderRepository;
    }


    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public MarketDtoIMPL addOrder(MarketDto orderDto, Authentication authentication) {

        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();

        Market order = marketMapper.toMarket(orderDto);

        order.setUsername(user.getUsername());
        Market save = orderRepository.save(order);
        return marketMapper.toDtoIMPL(save);
    }

    @Transactional(readOnly = true)
    public MarketDtoIMPL findbyId(Long marketId) {
        Market order = orderRepository.findById(marketId).orElseThrow(() -> new MarketNotFound("Market not found"));
        return marketMapper.toDtoIMPL(order);
    }


    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @orderSecurity.isOwner(#marketId, authentication)")
    public void deleteById(Long marketId) {

        Market order = orderRepository.findById(marketId).orElseThrow(() -> new MarketNotFound("Market not found"));
        orderRepository.delete(order);

    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @orderSecurity.isOwner(#marketId, authentication)")
    public MarketDtoIMPL updateOrder(Long marketId, MarketDto marketDtoDto) {
        Market order = orderRepository.findById(marketId).orElseThrow(() -> new MarketNotFound("Market not found"));
        if (marketDtoDto.getName() != null) {
            order.setName(marketDtoDto.getName());
        }
        if (marketDtoDto.getAmount()!=null) {
            order.setAmount(marketDtoDto.getAmount());
        }
        Market saved = orderRepository.save(order);

        return marketMapper.toDtoIMPL(saved);

    }
    @Transactional
    public void updateAmount(Long id, BigDecimal price){
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be valid");
        }
         orderRepository.updateAmount(id,price);
    }

}
