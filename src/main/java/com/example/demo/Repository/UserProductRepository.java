package com.example.demo.Repository;

import com.example.demo.Entity.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProductRepository extends JpaRepository<UserProduct,Long> {
}
