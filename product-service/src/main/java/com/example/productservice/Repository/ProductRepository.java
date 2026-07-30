package com.example.productservice.Repository;

import com.example.productservice.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product>findByMarketId(Long id);
@Modifying

@Query("UPDATE Product p SET p.productCount = p.productCount - 1 WHERE p.id= :id AND p.productCount > 0")
    int updateCount(@Param("id") Long id);


}
