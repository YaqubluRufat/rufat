package com.example.demo.Repository;

import com.example.demo.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products,Long> {
    boolean existsByIdAndMarketUserId(Long id,Long userId);
    List<Products> findByNameContaining(String name);
    boolean existsByNameAndMarketUserId(String name,Long userId);


}
