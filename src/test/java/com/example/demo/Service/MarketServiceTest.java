package com.example.demo.Service;

import com.example.demo.Dto.MarketDto;
import com.example.demo.Dto.MarketDtoIMPL;
import com.example.demo.Entity.Market;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.MarketMapper;
import com.example.demo.Repository.MarketRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Security.MyUserDetails;
import com.example.demo.ServiceKafka.Market.MarketDeleteEvent;
import com.example.demo.ServiceKafka.Market.MarketProducerEvent;
import com.example.demo.ServiceKafka.Market.MarketSavedEvent;
import com.example.demo.ServiceKafka.Market.MarketUpdateEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarketServiceTest {
    @Mock
    MarketRepository marketRepository;
    @Mock
    MarketMapper marketMapper;
    @InjectMocks
    MarketService marketService;
    @Mock
    Authentication authentication;
    @Mock
    UserRepository userRepository;
    @Mock
    MarketProducerEvent marketProducerEvent;

    @Test
    void addMarket() {
        MarketDto marketDto = new MarketDto();

        MyUserDetails user = mock(MyUserDetails.class);
        when(user.getId()).thenReturn(1L);

        User userEntity = new User();
        userEntity.setId(1L);

        Market market = new Market();

        Market savedMarket = new Market();
        savedMarket.setId(1L);
        savedMarket.setName("Rufat");
        savedMarket.setLocation("Baku");

        MarketDtoIMPL response = new MarketDtoIMPL();

        when(authentication.getPrincipal()).thenReturn(user);
        when(marketMapper.toMarket(marketDto)).thenReturn(market);
        when(userRepository.getReferenceById(1L)).thenReturn(userEntity);
        when(marketRepository.save(market)).thenReturn(savedMarket);
        doNothing().when(marketProducerEvent).publishMarketSaved(any(MarketSavedEvent.class));
        when(marketMapper.toDtoIMPL(savedMarket)).thenReturn(response);

        MarketDtoIMPL result = marketService.addMarket(marketDto, authentication);

        assertSame(response, result);

        verify(authentication).getPrincipal();
        verify(marketMapper).toMarket(marketDto);
        verify(userRepository).getReferenceById(1L);


    }




    @Test
    void findById() {
        Market market = new Market();
        market.setId(1L);

        MarketDtoIMPL marketDtoIMPL = new MarketDtoIMPL();

        when(marketRepository.findMarketById(1L)).thenReturn(Optional.of(market));
        when(marketMapper.toDtoIMPL(market)).thenReturn(marketDtoIMPL);

        MarketDtoIMPL byId = marketService.findById(1L);

        assertEquals(marketDtoIMPL,byId);

        verify(marketRepository).findMarketById(1L);
        verify(marketMapper).toDtoIMPL(market);


    }

    @Test
    void deleteById() {
        Market market = new Market();
        market.setId(1L);
        market.setName("Bravo");
        market.setLocation("Yasamal");


        when(marketRepository.findById(1L)).thenReturn(Optional.of(market));
        doNothing().when(marketProducerEvent).publishMarketDelete(any(MarketDeleteEvent.class));

        marketService.deleteById(1L);

        verify(marketRepository).findById(1L);

    }

    @Test
    void updateMarket() {
        Market market = new Market();
        market.setId(1L);
        market.setName("Bravo");
        market.setLocation("Yasamal");

        MarketDto marketDto = new MarketDto();



        MarketDtoIMPL marketDtoIMPL = new MarketDtoIMPL();

        when(marketRepository.findMarketById(1L)).thenReturn(Optional.of(market));
        doNothing().when(marketProducerEvent).publishMarketUpdate(any(MarketUpdateEvent.class));
        when(marketMapper.toDtoIMPL(market)).thenReturn(marketDtoIMPL);

        MarketDtoIMPL marketDtoIMPL1 = marketService.updateMarket(1L, marketDto);
        assertEquals(marketDtoIMPL1,marketDtoIMPL);

        verify(marketRepository).findMarketById(1L);
        verify(marketMapper).toDtoIMPL(market);


    }
}