package com.example.ordservice.FeignClient;

import com.example.ordservice.Dto.MarketDtoIMPL;
import com.example.ordservice.Dto.ProductDtoIMPL;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "product-service", url = "http://localhost:8092")
public interface ProductClient {

    @GetMapping("/product/find/{id}")
    ProductDtoIMPL findById(@PathVariable Long id);

    @PutMapping("/product/update/{id}")
    void updateCount(@PathVariable Long id);
}
