package com.example.demo.Repository;

import com.example.demo.Security.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken r Set r.isUsed=true Where r.username= :username")
    void markAllByUsername(@Param("username")String username);

    Optional<RefreshToken>findByToken(String refreshToken);
}
