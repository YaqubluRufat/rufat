package com.example.demo.Repository;

import com.example.demo.Dto.TokenDto;
import com.example.demo.Security.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken,Long> {
    @Transactional
    @Modifying
    @Query("UPDATE RefreshToken r SET r.isUsed=true Where r.username= :username")
    void markAllByUsername(@Param("username") String username);
    Optional<RefreshToken>findByToken(String refreshToken);
}
