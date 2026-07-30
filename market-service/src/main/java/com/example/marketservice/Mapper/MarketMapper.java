package com.example.marketservice.Mapper;

import com.example.marketservice.DTO.MarketDto;
import com.example.marketservice.DTO.MarketDtoIMPL;
import com.example.marketservice.Entity.Market;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarketMapper {

    Market toMarket(MarketDto marketDto);

    MarketDtoIMPL toDtoIMPL(Market market);
}
