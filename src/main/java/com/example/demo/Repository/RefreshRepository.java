package com.example.demo.Repository;

import com.example.demo.Security.RefreshToken;
import jakarta.transaction.Transactional;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RefreshRepository extends JpaRepository<RefreshToken, Long> {
    @Transactional
    @Query("UPDate RefreshToken r SET r.isUsed=true WHERE r.username =:username")
    void markAllIsUsedByUsername(String username);
}
