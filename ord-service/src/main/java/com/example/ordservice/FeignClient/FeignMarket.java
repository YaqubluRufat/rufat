package com.example.ordservice.FeignClient;

import com.example.ordservice.Dto.MarketDtoIMPL;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@FeignClient(name = "market-service", url = "http://localhost:8091")
public interface FeignMarket {

    @GetMapping("/market/find/{marketId}")
    MarketDtoIMPL findById(@PathVariable Long marketId);

    @PutMapping("/market/update/{id}")
    void updateAmount(@PathVariable Long id, @RequestBody BigDecimal price);

}
