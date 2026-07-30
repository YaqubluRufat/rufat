package com.example.productservice.Service;

import com.example.productservice.DTO.MarketDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "market-service", url = "http://localhost:8091")
public interface MarketClient {
    @GetMapping("/market/find/{marketId}")
    MarketDto findById(@PathVariable Long marketId);

    @GetMapping("/market/extract")
    boolean extractByIdAndUsername( @RequestParam Long marketId,@RequestParam String username);
}
