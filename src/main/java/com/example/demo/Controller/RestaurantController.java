package com.example.demo.Controller;

import com.example.demo.Dto.RestaurantDto;
import com.example.demo.Dto.RestaurantDtoIMPL;
import com.example.demo.Service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/save")
    public ResponseEntity<RestaurantDtoIMPL>addRestaurant(@RequestBody RestaurantDto restaurantDto, Authentication authentication){
        RestaurantDtoIMPL restaurantDtoIMPL = restaurantService.addRestaurant(restaurantDto, authentication);
        return ResponseEntity.ok(restaurantDtoIMPL);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?>deleteMyRestaurant(Authentication authentication){
        restaurantService.deleteMyRestaurant(authentication);
        return ResponseEntity.ok("Deleted successfully");
    }
    @GetMapping("/find/my")
    public ResponseEntity<RestaurantDtoIMPL>findTyRestaurant(Authentication authentication){
        RestaurantDtoIMPL myRestaurant = restaurantService.findMyRestaurant(authentication);
        return ResponseEntity.ok(myRestaurant);
    }
    @PutMapping("/update")
    public ResponseEntity<RestaurantDtoIMPL>updateRestaurant(@RequestBody @Valid RestaurantDto restaurantDto,Authentication authentication){
        RestaurantDtoIMPL restaurantDtoIMPL = restaurantService.updateRestaurant(restaurantDto, authentication);
        return ResponseEntity.ok(restaurantDtoIMPL);
    }

}


