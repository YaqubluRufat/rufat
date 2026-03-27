package com.example.demo.DTO;

import com.example.demo.Entity.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDto {
    @NotBlank(message = "cannot be empty")
    @Size(min = 3, max = 20, message = "size must be between 3 and 20")
    private String name;
    @NotNull(message = "cannot be empty")
    private List<Long> userId;

}
