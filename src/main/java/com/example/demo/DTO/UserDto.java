package com.example.demo.DTO;

import com.example.demo.Entity.Department;
import com.example.demo.Entity.UserProduct;
import com.example.demo.Entity.UserProfile;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
@NotBlank(message = "cannot be empty")
    private String name;
@NotNull(message = "cannot be empty")
    private Long userProfileId;




}
