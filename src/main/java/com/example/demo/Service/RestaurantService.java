package com.example.demo.Service;

import com.example.demo.Dto.RestaurantDto;
import com.example.demo.Dto.RestaurantDtoIMPL;
import com.example.demo.Entity.Restaurant;
import com.example.demo.Entity.User;
import com.example.demo.Exception.InvalidRole;
import com.example.demo.Exception.InvalidSort;
import com.example.demo.Exception.RestaurantNotFound;
import com.example.demo.Mapper.RestaurantMapper;
import com.example.demo.Repository.RestaurantRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public RestaurantService(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }
   @CacheEvict(value = "restaurants",allEntries = true)
    @PreAuthorize("hasRole('OWNER')")
    public RestaurantDtoIMPL addRestaurant(RestaurantDto restaurantDto, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        Restaurant restaurant = restaurantMapper.toRestaurant(restaurantDto);
        restaurant.setUser(user);
        Restaurant save = restaurantRepository.save(restaurant);
        return restaurantMapper.toRestaurantIMPL(save);

    }
    @CacheEvict(value = "restaurants",key = "#authentication.name")
    @PreAuthorize("hasRole('OWNER')")
    public void deleteMyRestaurant(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        if (user.getRestaurant() == null) {
            throw new RestaurantNotFound("Restaurant Not found");
        }
        Restaurant restaurant = user.getRestaurant();
        if (!user.getId().equals(restaurant.getUser().getId())) {
            throw new InvalidRole("Its not your role");
        }

        restaurantRepository.delete(restaurant);
    }
    @Cacheable(value = "restaurants",key = "#authentication.name")
    @PreAuthorize("hasRole('OWNER')")
    public RestaurantDtoIMPL findMyRestaurant(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        Restaurant restaurant = Optional.ofNullable(user.getRestaurant()).orElseThrow(() ->
                new RestaurantNotFound("Restaurant not found"));
        if (!user.getId().equals(restaurant.getUser().getId())) {
            throw new InvalidRole("Its not your role");
        }
        return restaurantMapper.toRestaurantIMPL(restaurant);
    }
    @CacheEvict(value = "restaurants",key = "#authentication.name")
    @PreAuthorize("hasRole('OWNER')")
    public RestaurantDtoIMPL updateRestaurant(RestaurantDto restaurantDto, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));


        Restaurant restaurant = Optional.ofNullable(user.getRestaurant()).orElseThrow(() ->
                new RestaurantNotFound("Restaurant not found"));
        boolean owner = user.getId().equals(restaurant.getUser().getId());
        if(!owner){
            throw new InvalidRole("Its not your role");
        }

        if (restaurantDto.getName() != null) {
            restaurant.setName(restaurantDto.getName());
        }
        if (restaurantDto.getLocation() != null) {
            restaurant.setLocation(restaurantDto.getLocation());
        }
        if (restaurantDto.getTelephonePhone() != null) {
            restaurant.setTelephonePhone(restaurantDto.getTelephonePhone());
        }
        Restaurant save = restaurantRepository.save(restaurant);
        return restaurantMapper.toRestaurantIMPL(save);

    }

}
