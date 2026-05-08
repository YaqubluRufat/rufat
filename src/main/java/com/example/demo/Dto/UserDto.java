package com.example.demo.Dto;

import com.example.demo.Entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotBlank(message = "cannot be empty")
    @Column(nullable = false)
    @Min(value = 2,message = "Must be at least 2 characters long")
    @Max(value = 10,message = "Must be at most 10 characters")
    private String username;
    @NotBlank(message = "cannot be empty")
    @Min(value = 2,message = "Must be at least 2 characters long")
    @Max(value = 10,message = "Must be at most 10 characters")
    private String password;

}
