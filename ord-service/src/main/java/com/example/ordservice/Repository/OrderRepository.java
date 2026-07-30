package com.example.ordservice.Repository;

import com.example.ordservice.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

    boolean existsByIdAndUserId(Long id, Long userId);
}
