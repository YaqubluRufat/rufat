package com.example.demo.Service;

import com.example.demo.Dto.MarketDto;
import com.example.demo.Dto.MarketDtoIMPL;
import com.example.demo.Entity.Market;
import com.example.demo.Exception.MarketNotFound;
import com.example.demo.Mapper.MarketMapper;
import com.example.demo.Repository.MarketRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.MyUserDetails;


import com.example.demo.ServiceKafka.Market.MarketDeleteEvent;
import com.example.demo.ServiceKafka.Market.MarketProducerEvent;
import com.example.demo.ServiceKafka.Market.MarketSavedEvent;
import com.example.demo.ServiceKafka.Market.MarketUpdateEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketMapper marketMapper;
    private final UserRepository userRepository;
    private final MarketProducerEvent marketProducerEvent;


    public MarketService(MarketRepository marketRepository, MarketMapper marketMapper,
                         UserRepository userRepository, MarketProducerEvent marketProducerEvent) {
        this.marketRepository = marketRepository;
        this.marketMapper = marketMapper;
        this.userRepository = userRepository;

        this.marketProducerEvent = marketProducerEvent;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public MarketDtoIMPL addMarket(MarketDto marketDto, Authentication authentication) {
        MyUserDetails user = (MyUserDetails) authentication.getPrincipal();

        Market market = marketMapper.toMarket(marketDto);
        market.setUser(userRepository.getReferenceById(user.getId()));
        Market save = marketRepository.save(market);
        MarketSavedEvent marketSavedEvent = new MarketSavedEvent(save.getId(), save.getName(), save.getLocation(), LocalDateTime.now());
        marketProducerEvent.publishMarketSaved(marketSavedEvent);
        return marketMapper.toDtoIMPL(save);
    }

    @Transactional(readOnly = true)
    public MarketDtoIMPL findById(Long id) {
        Market market = marketRepository.findMarketById(id).orElseThrow(() -> new MarketNotFound("Market not found"));

        return marketMapper.toDtoIMPL(market);

    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @marketSecurity.isOwner(#marketId,authentication)")
    public void deleteById(Long marketId) {
        Market market = marketRepository.findById(marketId).orElseThrow(() -> new MarketNotFound("Merket not found"));
        MarketDeleteEvent marketDeleteEvent = new MarketDeleteEvent(market.getId(), market.getName(), market.getLocation(),
                LocalDateTime.now());
        marketRepository.delete(market);
        marketProducerEvent.publishMarketDelete(marketDeleteEvent);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @marketSecurity.isOwner(#marketId,authentication)")
    public MarketDtoIMPL updateMarket(Long marketId, MarketDto marketDto) {
        Market market = marketRepository.findMarketById(marketId).orElseThrow(() -> new MarketNotFound("Market not found"));
        if (marketDto.getName() != null) {
            market.setName(marketDto.getName());
        }
        if (marketDto.getLocation() != null) {
            market.setLocation(marketDto.getLocation());
        }

        MarketUpdateEvent marketUpdateEvent = new MarketUpdateEvent(market.getId(), market.getName(), market.getLocation(),
                LocalDateTime.now());
        marketProducerEvent.publishMarketUpdate(marketUpdateEvent);
        return marketMapper.toDtoIMPL(market);

    }
}
