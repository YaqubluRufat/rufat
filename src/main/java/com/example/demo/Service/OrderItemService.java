package com.example.demo.Service;

import com.example.demo.Dto.OrderItemDto;
import com.example.demo.Dto.OrderItemDtoIMPL;
import com.example.demo.Entity.*;
import com.example.demo.Exception.*;

import com.example.demo.Mapper.OrderItemMapper;
import com.example.demo.Repository.MenuItemRepository;
import com.example.demo.Repository.OrderItemRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderRepository orderRepository, OrderItemMapper orderItemMapper, UserRepository userRepository, MenuItemRepository menuItemRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemMapper = orderItemMapper;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public User getCurrentUser(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
    }

    @CachePut(value = "orderItem", key = "#authentication.name + ':' + #result.id")
    @PreAuthorize("hasRole('USER')")
    public OrderItemDtoIMPL addOrderItem(OrderItemDto orderItemDto, Authentication authentication) {
        User user = getCurrentUser(authentication);
        MenuItem menuItem = menuItemRepository.findById(orderItemDto.getMenuItemId()).orElseThrow(() ->
                new MenuItemNotFound("MenuItem not found"));
        Order order = orderRepository.findById(orderItemDto.getOrderId()).orElseThrow(() ->
                new OrderNotFound("Order not found"));
        OrderItem orderItem = orderItemMapper.toOrderItem(orderItemDto);
        if (!user.getId().equals(order.getUser().getId())) {
            throw new InvalidRole("Its not for you");
        }
        orderItem.setOrder(order);
        orderItem.setMenuItem(menuItem);
        OrderItem save = orderItemRepository.save(orderItem);
        return orderItemMapper.toOrderItemDtoIMPL(save);
    }

    @CacheEvict(value = "orderItem", key = "#authentication.name + ':' + #id")
    @PreAuthorize("hasAnyRole('USER','ADMIN','OWNER')")
    public void deleteOrderItemDto(Long id, Authentication authentication) {
        User user = getCurrentUser(authentication);
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() ->
                new OrderItemNotFound("Order item not found"));
        boolean user1 = user.getId().equals(orderItem.getOrder().getUser().getId());
        boolean owner = user.getId().equals(orderItem.getOrder().getRestaurant().getUser().getId());
        boolean admin = user.getRole().equals(Role.ADMIN);
        if (!owner && !admin && !user1) {
            throw new InvalidRole("Its not your role");
        }
        orderItemRepository.delete(orderItem);
    }

    @CachePut( value = "orderItem", key = "#authentication.name + ':' + #result.id")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public OrderItemDtoIMPL updateOrderDtoIMPL(Long id, Authentication authentication, OrderItemDto orderItemDto) {
        User user = getCurrentUser(authentication);

        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new OrderItemNotFound("Order item not found"));

        boolean user1 = user.getId().equals(orderItem.getOrder().getUser().getId());
        boolean roleAdmin = authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_ADMIN"));


        if (!user1 && !roleAdmin) {
            throw new InvalidRole("Its not your role");
        }
        if (orderItemDto.getMenuItemId() != null) {
            MenuItem menuItem = menuItemRepository.findById(orderItemDto.getMenuItemId()).orElseThrow(() ->
                    new MenuItemNotFound("Menu item not found"));

            orderItem.setMenuItem(menuItem);
        }
        if (orderItemDto.getSay() != null) {
            orderItem.setSay(orderItemDto.getSay());
        }
        OrderItem save = orderItemRepository.save(orderItem);
        return orderItemMapper.toOrderItemDtoIMPL(save);
    }

    @Cacheable(value = "orderItem", key = "#authentication.name + ':' + #id",sync = true)
    @PreAuthorize("hasAnyRole('USER','OWNER')")
    public OrderItemDtoIMPL findById(Long id, Authentication authentication) {
        User user = getCurrentUser(authentication);
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new OrderItemNotFound("Order Item not found"));
        boolean owner = user.getId().equals(orderItem.getOrder().getRestaurant().getUser().getId());
        boolean user1 = user.getId().equals(orderItem.getOrder().getUser().getId());
        if (!owner && !user1) {
            throw new InvalidRole("Its nto your role");
        }
        return orderItemMapper.toOrderItemDtoIMPL(orderItem);
    }

}

