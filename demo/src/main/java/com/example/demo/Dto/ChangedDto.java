package com.example.demo.Dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangedDto {
    private String oldPassword;
    private String newPassword;
}
