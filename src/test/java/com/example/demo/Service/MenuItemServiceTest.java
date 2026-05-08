package com.example.demo.Service;

import com.example.demo.Dto.MenuItemDto;
import com.example.demo.Dto.MenuItemDtoIMPL;
import com.example.demo.Entity.MenuItem;
import com.example.demo.Entity.Restaurant;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.MenuItemMapper;
import com.example.demo.Repository.MenuItemRepository;
import com.example.demo.Repository.RestaurantRepository;
import com.example.demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuItemServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    MenuItemRepository menuItemRepository;
    @Mock
    RestaurantRepository restaurantRepository;
    @InjectMocks
    MenuItemService menuItemService;
    @Mock
    Authentication authentication;
    @Mock
    MenuItemMapper menuItemMapper;

    @Test
    void addMenuItem() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");
        Restaurant restaurant = new Restaurant();
        restaurant.setId(10L);
        user.setRestaurant(restaurant);
        restaurant.setUser(user);
        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurant(restaurant);
        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemDto.setRestaurantId(10L);
        MenuItemDtoIMPL menuItemDtoIMPL = new MenuItemDtoIMPL();
        MenuItem savedMenuItem = new MenuItem();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(restaurant));
        when(menuItemMapper.toMenuItem(menuItemDto)).thenReturn(menuItem);
        when(menuItemRepository.save(menuItem)).thenReturn(savedMenuItem);
        when(menuItemMapper.toMenuItemDtoIMPL(savedMenuItem)).thenReturn(menuItemDtoIMPL);

        MenuItemDtoIMPL menuItemDtoIMPL1 = menuItemService.addMenuItem(menuItemDto, authentication);
        assertEquals(menuItemDtoIMPL1,menuItemDtoIMPL);
         verify(userRepository).findByUsername("rufat");
         verify(menuItemMapper).toMenuItem(menuItemDto);
         verify(menuItemRepository).save(menuItem);


    }
    @Test
    void findById() {

        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);

        MenuItemDtoIMPL menuItemDtoIMPL = new MenuItemDtoIMPL();

        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(menuItemMapper.toMenuItemDtoIMPL(menuItem)).thenReturn(menuItemDtoIMPL);

        MenuItemDtoIMPL byId = menuItemService.findById(1L);
        assertEquals(menuItemDtoIMPL,byId);

        verify(menuItemRepository).findById(1L);
        verify(menuItemMapper).toMenuItemDtoIMPL(menuItem);

    }

    @Test
    void deleteMenuItem() {
        User user = new User();
        user.setId(1L);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(10L);
        restaurant.setUser(user);
        MenuItem menuItem = new MenuItem();
        menuItem.setId(11L);
        menuItem.setRestaurant(restaurant);
        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(menuItemRepository.findById(11L)).thenReturn(Optional.of(menuItem));

        menuItemService.deleteMenuItem(11L,authentication);
        verify(userRepository).findByUsername("rufat");
        verify(menuItemRepository).findById(11L);



    }
    @Test
    void updateMenuItem() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");
        Restaurant restaurant = new Restaurant();
        restaurant.setId(10L);
        user.setRestaurant(restaurant);
        restaurant.setUser(user);
        MenuItem menuItem = new MenuItem();
        menuItem.setId(2L);
        menuItem.setRestaurant(restaurant);
        MenuItemDto menuItemDto = new MenuItemDto();
        menuItemDto.setRestaurantId(10L);
        MenuItemDtoIMPL menuItemDtoIMPL = new MenuItemDtoIMPL();
        MenuItem savedMenuItem = new MenuItem();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(menuItemRepository.findById(2L)).thenReturn(Optional.of(menuItem));
        when(menuItemRepository.save(menuItem)).thenReturn(savedMenuItem);
        when(menuItemMapper.toMenuItemDtoIMPL(savedMenuItem)).thenReturn(menuItemDtoIMPL);
        MenuItemDtoIMPL menuItemDtoIMPL1 = menuItemService.updateMenuItem(2L, menuItemDto, authentication);
        assertEquals(menuItemDtoIMPL,menuItemDtoIMPL1);

        verify(userRepository).findByUsername("rufat");
        verify(menuItemRepository).findById(2L);
        verify(menuItemRepository).save(menuItem);

    }
}