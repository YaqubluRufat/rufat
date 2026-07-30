package com.example.ordservice.Service;

import com.example.ordservice.Dto.MarketDtoIMPL;
import com.example.ordservice.Dto.OrderDto;
import com.example.ordservice.Dto.OrderDtoIMPL;
import com.example.ordservice.Dto.ProductDtoIMPL;
import com.example.ordservice.Entity.Order;
import com.example.ordservice.FeignClient.FeignMarket;
import com.example.ordservice.FeignClient.ProductClient;
import com.example.ordservice.Mapper.OrderMapper;
import com.example.ordservice.Repository.OrderRepository;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {

    private final FeignMarket feignMarket;
    private final ProductClient productClient;
    private final OrderMapper  orderMapper;
    private final OrderRepository orderRepository;

    public OrderService(FeignMarket feignMarket, ProductClient productClient, OrderMapper orderMapper, OrderRepository orderRepository) {
        this.feignMarket = feignMarket;
        this.productClient = productClient;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    public OrderDtoIMPL addOrder(OrderDto orderDto){
        Order order = orderMapper.toOrder(orderDto);

        MarketDtoIMPL market = feignMarket.findById(orderDto.getMarketId());
        ProductDtoIMPL product = productClient.findById(orderDto.getProductId());


        productClient.updateCount(product.getId());
        feignMarket.updateAmount(market.getId(),product.getPrice());


        Order save = orderRepository.save(order);
        return orderMapper.toDtoIMPL(save);

    }
    @PreAuthorize("hasRole('ADMIN') or @orderSecurity.isUser(#id,authentication)")
    public OrderDtoIMPL findById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.toDtoIMPL(order);

    }
}
