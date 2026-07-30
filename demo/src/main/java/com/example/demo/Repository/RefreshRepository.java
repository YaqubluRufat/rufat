package com.example.demo.Repository;

import com.example.demo.Security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<RefreshToken,Long> {
    @Transactional
    @Modifying
    @Query("UPDATE RefreshToken r SET r.isUsed=true WHERE  r.username= :username")
    void markAllByUsername(String username);
    Optional<RefreshToken> findByToken(String refreshToken);
}
