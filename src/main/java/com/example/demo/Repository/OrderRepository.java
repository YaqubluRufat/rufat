package com.example.demo.Repository;

import com.example.demo.Entity.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Transactional
    @Query(value = "SELECT o From Order o  WHERE o.id= :id AND o.user.id= :userId")
    Optional<Order>findByIdAndUserId(Long id,Long userId);

}
