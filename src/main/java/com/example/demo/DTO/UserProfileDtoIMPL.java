package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDtoIMPL {
    private Long userProfileId;
    private String name;
    private String username;
    private String email;
    private boolean active;


}
