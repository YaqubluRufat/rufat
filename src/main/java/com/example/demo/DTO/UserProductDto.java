package com.example.demo.DTO;

import com.example.demo.Entity.ProductType;
import com.example.demo.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProductDto {
    @Positive
    @NotNull(message = "cannot be empty")
    private Long UserId;
    @NotNull(message = "cannot be empty")
    private Long productTypeId;
}
