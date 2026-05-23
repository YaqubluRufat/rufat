package com.example.demo.Repository;

import com.example.demo.Entity.Market;
import com.example.demo.Mapper.MarketMapper;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market,Long> {

    boolean existsByIdAndUserId(Long marketId,Long userId);
    @Query("SELECT m FROM Market m LEFT JOIN FETCH m.products  WHERE m.id= :id")
    Optional<Market>findMarketById(@Param("id") Long id);


}
