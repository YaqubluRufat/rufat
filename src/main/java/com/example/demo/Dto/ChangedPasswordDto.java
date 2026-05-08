package com.example.demo.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangedPasswordDto {
    @NotNull(message = "cannot be empty")
    private String getNewPassword;
    @NotNull(message = "cannot be empty")
    private String getOldPassword;
}
