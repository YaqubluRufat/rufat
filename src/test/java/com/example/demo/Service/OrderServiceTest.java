package com.example.demo.Service;

import com.example.demo.Dto.OrderDto;
import com.example.demo.Dto.OrderDtoIMPL;
import com.example.demo.Entity.*;
import com.example.demo.Mapper.OrderMapper;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderMapper orderMapper;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    OrderService orderService;
    @Mock
    Authentication authentication;

    @Test
    void addOrder() {
        User user = new User();
        user.setUsername("rufat");
        Order order = new Order();
        order.setUser(user);
        OrderDto orderDto = new OrderDto();
        OrderDtoIMPL orderDtoIMPL = new OrderDtoIMPL();
       MenuItem menuItem = new MenuItem();
       menuItem.setPrice(BigDecimal.valueOf(20));
       OrderItem orderItem = new OrderItem();
       orderItem.setSay(20L);
        orderItem.setMenuItem(menuItem);
        orderItem.setOrder(order);
        order.setOrderItem(List.of(orderItem));
        Order savedOrder = new Order();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(orderMapper.toOrder(orderDto)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(savedOrder);
        when(orderMapper.toOrderDtoIMPL(savedOrder)).thenReturn(orderDtoIMPL);

        OrderDtoIMPL orderDtoIMPL1 = orderService.addOrder(orderDto, authentication);
        assertEquals(orderDtoIMPL,orderDtoIMPL1);

        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");
        verify(orderMapper).toOrder(orderDto);
        verify(orderRepository).save(order);

    }

    @Test
    void findMyId() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");
        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        OrderDtoIMPL orderDtoIMPL = new OrderDtoIMPL();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(authentication.getAuthorities()).thenReturn(Collections.emptyList());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderMapper.toOrderDtoIMPL(order)).thenReturn(orderDtoIMPL);

        OrderDtoIMPL myId = orderService.findMyId(1L, authentication);
        assertEquals(orderDtoIMPL,myId);
        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");
        verify(orderRepository).findById(1L);
        verify(orderMapper).toOrderDtoIMPL(order);

    }
    @Test
    void updateStatus() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");
        Restaurant restaurant = new Restaurant();
        restaurant.setUser(user);
        Order order = new Order();
        order.setStatus(Status.CREATED);
        order.setId(2L);
        order.setRestaurant(restaurant);
        Order savedOrder = new Order();
        OrderDtoIMPL orderDtoIMPL = new OrderDtoIMPL();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(orderRepository.findById(2L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(savedOrder);
        when(orderMapper.toOrderDtoIMPL(savedOrder)).thenReturn(orderDtoIMPL);

        OrderDtoIMPL orderDtoIMPL1 = orderService.updateStatus(2L, String.valueOf(Status.CREATED), authentication);

        assertEquals(orderDtoIMPL,orderDtoIMPL1);

        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");
        verify(orderRepository).findById(2L);

    }

    @Test
    void deleteOrder() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");
        Order order = new Order();
        order.setId(1L);

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(orderRepository.findByIdAndUserId(1L,1L)).thenReturn(Optional.of(order));
        orderService.deleteOrder(1L,authentication);
        verify(userRepository).findByUsername("rufat");
        verify(orderRepository).findByIdAndUserId(1L,1L);
    }
}