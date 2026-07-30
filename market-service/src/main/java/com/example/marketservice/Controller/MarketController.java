package com.example.marketservice.Controller;

import com.example.marketservice.DTO.MarketDto;
import com.example.marketservice.DTO.MarketDtoIMPL;
import com.example.marketservice.Repository.MarketRepository;
import com.example.marketservice.Service.MarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/market")
public class MarketController {
    private final MarketService marketService;
    private final MarketRepository marketRepository;

    public MarketController(MarketService marketService, MarketRepository marketRepository) {
        this.marketService = marketService;
        this.marketRepository = marketRepository;
    }


    @PostMapping("/save")
    public ResponseEntity<MarketDtoIMPL>addOrder(@RequestBody MarketDto orderDto, Authentication authentication){
        MarketDtoIMPL orderDtoIMPL = marketService.addOrder(orderDto, authentication);
        return ResponseEntity.ok().body(orderDtoIMPL);
    }
    @DeleteMapping("/delete/{marketId}")
    public ResponseEntity<?>deleteById(@PathVariable Long marketId){
        marketService.deleteById(marketId);
        return ResponseEntity.ok().body("Deleted");
    }
    @GetMapping("/find/{marketId}")
    public ResponseEntity<MarketDtoIMPL>findById(@PathVariable Long marketId){
        MarketDtoIMPL orderDtoIMPL = marketService.findbyId(marketId);
        return ResponseEntity.ok().body(orderDtoIMPL);
    }
    @PutMapping("/updates/{marketId}")
    public ResponseEntity<MarketDtoIMPL>updateById(@PathVariable Long marketId, @RequestBody MarketDto marketDtoDto){
        MarketDtoIMPL orderDtoIMPL = marketService.updateOrder(marketId, marketDtoDto);
        return ResponseEntity.ok().body(orderDtoIMPL);
    }
    @GetMapping("/extract")
    public boolean extractByIdAndUserId(@RequestParam Long marketId,@RequestParam String username){
        return marketRepository.existsByIdAndUsername(marketId,username);
    }
    @PutMapping("/update/{id}")
    public void updateAmount(@PathVariable Long id,@RequestBody BigDecimal price){
        marketService.updateAmount(id,price);

    }
}
