package com.example.demo.Security;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    private String token;
    private LocalDateTime expirityDate;
    private LocalDateTime createdDate = LocalDateTime.now();
    private boolean expired;
    private boolean isUsed = false;

    private boolean isexpired() {
        return LocalDateTime.now().isAfter(expirityDate);

    }

    public RefreshToken(LocalDateTime exspirityDate, String token, String username) {
        this.expirityDate = exspirityDate;
        this.token = token;
        this.username = username;
    }
}
