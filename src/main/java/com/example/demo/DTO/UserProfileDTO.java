package com.example.demo.DTO;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    @NotBlank(message = "cannot be empty")
    private String name;
    @NotBlank(message = "cannot be empty")
    private String username;
    @NotBlank(message = "cannot be empty")
    private String email;
    @AssertTrue(message = "Profile must be active.")
    private boolean active;



}
