package com.example.demo.Service;

import com.example.demo.Dto.RestaurantDto;
import com.example.demo.Dto.RestaurantDtoIMPL;
import com.example.demo.Entity.Restaurant;
import com.example.demo.Entity.User;
import com.example.demo.Exception.RestaurantNotFound;
import com.example.demo.Mapper.RestaurantMapper;
import com.example.demo.Repository.RestaurantRepository;
import com.example.demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    Authentication authentication;
    @Mock
    RestaurantMapper restaurantMapper;
    @InjectMocks
    RestaurantService restaurantService;


    @Test
    void addRestaurant() {
        RestaurantDto restaurantDto = new RestaurantDto();
        User user = new User();
        user.setUsername("rufat");
        Restaurant restaurant = new Restaurant();
        RestaurantDtoIMPL restaurantDtoIMPL1 = new RestaurantDtoIMPL();
        Restaurant savedRestaurant = new Restaurant();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(restaurantMapper.toRestaurant(restaurantDto)).thenReturn(restaurant);
        when(restaurantRepository.save(restaurant)).thenReturn(savedRestaurant);
        when(restaurantMapper.toRestaurantIMPL(savedRestaurant)).thenReturn(restaurantDtoIMPL1);

        RestaurantDtoIMPL restaurantDtoIMPL = restaurantService.addRestaurant(restaurantDto, authentication);

        assertEquals(restaurantDtoIMPL1, restaurantDtoIMPL);

        verify(userRepository).findByUsername("rufat");
        verify(restaurantMapper).toRestaurant(restaurantDto);
        verify(restaurantRepository).save(restaurant);
        verify(restaurantMapper).toRestaurantIMPL(savedRestaurant);

    }

    @Test
    void RestaurantNotFound() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");
        user.setRestaurant(null);

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));


        assertThrows(RestaurantNotFound.class, () -> restaurantService.findMyRestaurant(authentication));


        verify(userRepository).findByUsername("rufat");

    }


    @Test
    void deleteMyRestaurant() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");
        Restaurant restaurant = new Restaurant();
        restaurant.setUser(user);
        user.setRestaurant(restaurant);
        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));

        restaurantService.deleteMyRestaurant(authentication);

        verify(userRepository).findByUsername("rufat");
        verify(restaurantRepository).delete(restaurant);


    }


    @Test
    void updateRestaurant() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");
        Restaurant restaurant = new Restaurant();
        user.setRestaurant(restaurant);
        restaurant.setUser(user);
        RestaurantDto restaurantDto = new RestaurantDto();
        RestaurantDtoIMPL restaurantDtoIMPL = new RestaurantDtoIMPL();
        Restaurant restaurantSaved = new Restaurant();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(restaurantRepository.save(restaurant)).thenReturn(restaurantSaved);
        when(restaurantMapper.toRestaurantIMPL(restaurantSaved)).thenReturn(restaurantDtoIMPL);

        RestaurantDtoIMPL restaurantDtoIMPL1 = restaurantService.updateRestaurant(restaurantDto, authentication);
        assertEquals(restaurantDtoIMPL,restaurantDtoIMPL1);
        verify(userRepository).findByUsername("rufat");
        verify(restaurantRepository).save(restaurant);

    }

}