package com.example.marketservice.Repository;

import com.example.marketservice.Entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface MarketRepository extends JpaRepository<Market,Long> {

    boolean existsByIdAndUsername(Long marketId, String username);
@Modifying
@Query("UPDATE Market m SET m.amount = m.amount + :price WHERE m.id= :id")
    void updateAmount(@Param("id") Long id, @Param("price") BigDecimal price);
}
