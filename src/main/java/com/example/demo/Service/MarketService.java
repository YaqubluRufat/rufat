package com.example.demo.Service;

import com.example.demo.Dto.MarketDto;
import com.example.demo.Dto.MarketDtoIMPL;
import com.example.demo.Entity.Market;
import com.example.demo.Exception.MarketNotFound;
import com.example.demo.Mapper.MarketMapper;
import com.example.demo.Repository.MarketRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.MyUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketMapper marketMapper;
    private final UserRepository userRepository;


    public MarketService(MarketRepository marketRepository, MarketMapper marketMapper, UserRepository userRepository) {
        this.marketRepository = marketRepository;
        this.marketMapper = marketMapper;
        this.userRepository = userRepository;
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public MarketDtoIMPL addMarket(MarketDto marketDto, Authentication authentication){
        MyUserDetails user = (MyUserDetails)authentication.getPrincipal();

        Market market = marketMapper.toMarket(marketDto);
        market.setUser(userRepository.getReferenceById(user.getId()));
        Market save = marketRepository.save(market);
        return marketMapper.toDtoIMPL(save);
    }
    public MarketDtoIMPL findById(Long id){
        Market market = marketRepository.findMarketById(id).orElseThrow(() -> new MarketNotFound("Market not found"));
        return marketMapper.toDtoIMPL(market);

    }
    @PreAuthorize("hasRole('ADMIN') or @marketSecurity.isOwner(#marketId,authentication)")
    public void deleteById(Long marketId){
        Market market = marketRepository.findById(marketId).orElseThrow(() -> new MarketNotFound("Merket not found"));

        marketRepository.delete(market);

    }
    @PreAuthorize("hasRole('ADMIN') or @marketSecurity.isOwner(#marketId,authentication)")
    public MarketDtoIMPL updateMarket(Long marketId,MarketDto marketDto){
        Market market = marketRepository.findMarketById(marketId).orElseThrow(() -> new MarketNotFound("Market not found"));
        if(marketDto.getName()!=null){
            market.setName(marketDto.getName());
        }
        if(marketDto.getLocation()!=null){
            market.setLocation(marketDto.getLocation());
        }
        Market save = marketRepository.save(market);
        return marketMapper.toDtoIMPL(save);

    }
}
