package com.example.demo.Controller;

import com.example.demo.Dto.MarketDto;
import com.example.demo.Dto.MarketDtoIMPL;
import com.example.demo.Service.MarketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market")
public class MarketController {
    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }
  @PostMapping("/save")
    public ResponseEntity<MarketDtoIMPL>addmarket(@RequestBody MarketDto dto, Authentication authentication){
        MarketDtoIMPL marketDtoIMPL = marketService.addMarket(dto, authentication);
        return ResponseEntity.ok(marketDtoIMPL);

    }
    @GetMapping("/find/{id}")
    public ResponseEntity<MarketDtoIMPL>findById(@PathVariable Long id){
        MarketDtoIMPL byId = marketService.findById(id);
        return ResponseEntity.ok(byId);
    }
    @DeleteMapping("/delete/{marketId}")
    public ResponseEntity<?>deletebyId(@PathVariable Long marketId){
        marketService.deleteById(marketId);
        return ResponseEntity.ok("deleted");
    }
    @PutMapping("/update/{marketId}")
    public ResponseEntity<MarketDtoIMPL>updateMarket(@PathVariable Long marketId,@RequestBody MarketDto marketDto){
        MarketDtoIMPL marketDtoIMPL = marketService.updateMarket(marketId, marketDto);
        return ResponseEntity.ok(marketDtoIMPL);
    }



}
