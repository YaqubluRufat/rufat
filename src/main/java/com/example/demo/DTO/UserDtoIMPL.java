package com.example.demo.DTO;

import com.example.demo.Entity.Department;
import com.example.demo.Entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoIMPL {
    private Long id;
    private String name;
    private UserProfileDtoIMPL userProfile;
}
