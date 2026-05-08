package com.example.demo.Service;

import com.example.demo.Dto.OrderItemDto;
import com.example.demo.Dto.OrderItemDtoIMPL;
import com.example.demo.Entity.*;
import com.example.demo.Mapper.OrderItemMapper;
import com.example.demo.Mapper.OrderMapper;
import com.example.demo.Repository.MenuItemRepository;
import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceTest {
    @Mock
    OrderItemRepository orderItemRepository;
    @Mock
    OrderItemMapper orderItemMapper;
    @Mock
    UserRepository userRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    MenuItemRepository menuItemRepository;
    @InjectMocks
    OrderItemService orderItemService;
    @Mock
    Authentication authentication;

    @Test
    void addOrderItem() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");

        Order order = new Order();
        order.setId(3L);
        order.setUser(user);

        OrderItem orderItem = new OrderItem();


        MenuItem menuItem = new MenuItem();
        menuItem.setId(2L);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderId(3L);
        orderItemDto.setMenuItemId(2L);

        OrderItemDtoIMPL orderItemDtoIMPL = new OrderItemDtoIMPL();

        OrderItem savedOrderItem = new OrderItem();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(menuItemRepository.findById(2L)).thenReturn(Optional.of(menuItem));
        when(orderRepository.findById(3L)).thenReturn(Optional.of(order));
        when(orderItemMapper.toOrderItem(orderItemDto)).thenReturn(orderItem);
        when(orderItemRepository.save(orderItem)).thenReturn(savedOrderItem);
        when(orderItemMapper.toOrderItemDtoIMPL(savedOrderItem)).thenReturn(orderItemDtoIMPL);

        OrderItemDtoIMPL orderItemDtoIMPL1 = orderItemService.addOrderItem(orderItemDto, authentication);
        assertEquals(orderItemDtoIMPL,orderItemDtoIMPL1);

        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");
        verify(menuItemRepository).findById(2L);
        verify(orderRepository).findById(3L);
        verify(orderItemMapper).toOrderItem(orderItemDto);
        verify(orderItemRepository).save(orderItem);


    }

    @Test
    void deleteOrderItemDto() {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.USER);
        user.setUsername("rufat");

        Order order = new Order();
        order.setId(2L);
        order.setUser(user);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(3L);
        orderItem.setOrder(order);

        Restaurant restaurant = new Restaurant();
        restaurant.setUser(user);
        order.setRestaurant(restaurant);

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(orderItemRepository.findById(3L)).thenReturn(Optional.of(orderItem));

        orderItemService.deleteOrderItemDto(3L,authentication);

        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");
        verify(orderItemRepository).findById(3L);

    }

    @Test
    void updateOrderDtoIMPL() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");

        Order order = new Order();
        order.setId(10L);
        order.setUser(user);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(5L);
        orderItem.setOrder(order);

        MenuItem menuItem = new MenuItem();
        menuItem.setId(2L);

        OrderItemDto dto = new OrderItemDto();
        dto.setMenuItemId(2L);
        dto.setSay(2L);

        OrderItem saved = new OrderItem();
        OrderItemDtoIMPL orderItemDtoIMPL= new OrderItemDtoIMPL();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(orderItemRepository.findById(5L)).thenReturn(Optional.of(orderItem));
        when(menuItemRepository.findById(2L)).thenReturn(Optional.of(menuItem));
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        when(orderItemRepository.save(orderItem)).thenReturn(saved);
        when(orderItemMapper.toOrderItemDtoIMPL(saved)).thenReturn(orderItemDtoIMPL);

        OrderItemDtoIMPL orderItemDtoIMPL1 = orderItemService.updateOrderDtoIMPL(5L, authentication, dto);
        assertEquals(orderItemDtoIMPL,orderItemDtoIMPL1);

        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");
        verify(orderItemRepository).findById(5L);
        verify(authentication).getAuthorities();


    }

    @Test
    void findById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");

        Restaurant restaurant = new Restaurant();
        restaurant.setUser(user);

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);

        OrderItem orderItem = new OrderItem();
        orderItem.setId(2L);
        orderItem.setOrder(order);

        OrderItemDtoIMPL orderItemDtoIMPL = new OrderItemDtoIMPL();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(orderItemRepository.findById(2L)).thenReturn(Optional.of(orderItem));
        when(orderItemMapper.toOrderItemDtoIMPL(orderItem)).thenReturn(orderItemDtoIMPL);

        OrderItemDtoIMPL byId = orderItemService.findById(2L, authentication);

        assertEquals(orderItemDtoIMPL,byId);

        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");
        verify(orderItemRepository).findById(2L);

    }
}