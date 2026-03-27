package com.example.demo.DTO;

import com.example.demo.Entity.ProductType;
import com.example.demo.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProductDtoIMPL {
    private Long id;

    private UserDtoIMPL user;

    private ProductTypeDtoIMPL productType;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
