package com.example.demo.Repository;

import com.example.demo.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r.rating FROM Review r WHERE r.rating= :rating ")
    List<Review>findByRating(Long rating);
}
