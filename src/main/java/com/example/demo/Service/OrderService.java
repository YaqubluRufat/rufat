package com.example.demo.Service;

import com.example.demo.Dto.OrderDto;
import com.example.demo.Dto.OrderDtoIMPL;
import com.example.demo.Dto.OrderItemDtoIMPL;
import com.example.demo.Dto.ValidationDto;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.Status;
import com.example.demo.Entity.User;
import com.example.demo.Exception.InvalidRole;
import com.example.demo.Exception.OrderNotFound;
import com.example.demo.Exception.UserNotFound;
import com.example.demo.Mapper.OrderMapper;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;


    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
    }
    @CachePut(value = "order",key = "#result.id")
    @PreAuthorize("hasRole('USER')")
    public OrderDtoIMPL addOrder(OrderDto orderDto, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        Order order = orderMapper.toOrder(orderDto);
        order.setUser(user);
        BigDecimal reduce = order.getOrderItem().stream().map(item ->
                item.getMenuItem().getPrice().multiply(BigDecimal.valueOf(item.getSay()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(reduce);
        Order save = orderRepository.save(order);
        return orderMapper.toOrderDtoIMPL(save);


    }
    @Cacheable(value = "order",key = "#id")
    @PreAuthorize("hasAnyRole('USER','ADMIN','OWNER')")
    public OrderDtoIMPL findMyId(Long id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFound("Order not found"));
        boolean user1 = user.getId().equals(order.getUser().getId());
        boolean adminOwner = authentication.getAuthorities().stream().anyMatch(
                p -> p.getAuthority().equals("ROLE_ADMIN") || p.getAuthority().equals("ROLE_OWNER")
        );
        if(!user1 && !adminOwner){
            throw new InvalidRole("Its not your role");
        }
        return orderMapper.toOrderDtoIMPL(order);

    }

    @CachePut(value = "order",key = "#id")
    @PreAuthorize("hasRole('OWNER')")
    public OrderDtoIMPL updateStatus(Long id, String newStatus, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new UserNotFound("User not found"));
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFound("Order not found"));
        if (!user.getId().equals(order.getRestaurant().getUser().getId())) {
            throw new InvalidRole("Its not your role");
        }
        order.setStatus(Status.valueOf(newStatus));
        Order save = orderRepository.save(order);
        return orderMapper.toOrderDtoIMPL(save);
    }
    @CacheEvict(value = "order",key = "#id")
    @PreAuthorize("hasRole('USER')")
    public void deleteOrder(Long id, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        Order order = orderRepository.findByIdAndUserId(id, user.getId()).orElseThrow(() -> new OrderNotFound("Order not found"));
        orderRepository.delete(order);
    }

}

