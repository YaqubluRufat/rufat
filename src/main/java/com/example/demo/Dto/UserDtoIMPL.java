package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoIMPL {
    private Long id;
    private String username;
    private String role;
    private LocalDateTime createdData;
    private LocalDateTime updatedDate;
}
