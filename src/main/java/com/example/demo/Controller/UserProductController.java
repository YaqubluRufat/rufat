package com.example.demo.Controller;

import com.example.demo.DTO.UserProductDto;
import com.example.demo.DTO.UserProductDtoIMPL;
import com.example.demo.Entity.UserProduct;
import com.example.demo.Service.UserProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api4")
public class UserProductController {
    private final UserProductService userProductService;

    public UserProductController(UserProductService userProductService) {
        this.userProductService = userProductService;
    }
    @PostMapping("/save")
    public ResponseEntity<UserProductDtoIMPL>addUserProduct(@RequestBody UserProductDto userProductDto){
        UserProductDtoIMPL userProductDtoIMPL = userProductService.addUserProduct(userProductDto);
        return ResponseEntity.ok().body(userProductDtoIMPL);
    }
}
