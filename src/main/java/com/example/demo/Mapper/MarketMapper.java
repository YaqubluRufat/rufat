package com.example.demo.Mapper;

import com.example.demo.Dto.MarketDto;
import com.example.demo.Dto.MarketDtoIMPL;
import com.example.demo.Entity.Market;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {ProductMapper.class})
public interface MarketMapper {

    Market toMarket(MarketDto marketDto);

    MarketDtoIMPL toDtoIMPL(Market market);
}
