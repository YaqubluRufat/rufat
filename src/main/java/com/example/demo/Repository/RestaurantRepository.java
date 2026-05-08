package com.example.demo.Repository;

import com.example.demo.Entity.Restaurant;
import com.example.demo.Security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

}
