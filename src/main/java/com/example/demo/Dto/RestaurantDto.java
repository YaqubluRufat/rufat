package com.example.demo.Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {
    @NotBlank(message = "cannot be empty")
    @Min(value = 2,message = "Must be at least 2 characters long")
    private String name;
    @NotBlank(message = "cannot be empty")
    @Min(value = 2,message = "Must be at least 2 characters long")
    private String location;

    @NotBlank(message = "cannot be empty")
    @Min(value = 4,message = "Must be at least 4 characters long")
    private String telephonePhone;

}
