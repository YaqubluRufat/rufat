package com.example.demo.DTO;

import com.example.demo.Entity.Department;
import com.example.demo.Entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDtoIMPL {

    private Long id;
    private String name;
    private List<UserDtoIMPL>users ;


}