package com.example.demo.Mapper;

import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.DTO.UserProfileDtoIMPL;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.UserProfile;
import com.example.demo.Exception.DepartmentNotFoundException;
import com.example.demo.Repository.DepartmentRepository;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {
    private final DepartmentRepository departmentRepository;

    public UserProfileMapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public UserProfile touserProfile(UserProfileDTO userProfileDTO){
        UserProfile userProfile= new UserProfile();
        userProfile.setName(userProfileDTO.getName());
        userProfile.setEmail(userProfileDTO.getEmail());
        userProfile.setUsername(userProfileDTO.getUsername());
        userProfile.setActive(userProfileDTO.isActive());



        return userProfile;

    }
    public UserProfileDtoIMPL toUserProfileDTOIMPL(UserProfile userProfile){
        UserProfileDtoIMPL userProfileDtoIMPL= new UserProfileDtoIMPL();
        userProfileDtoIMPL.setUserProfileId(userProfile.getId());
        userProfileDtoIMPL.setName(userProfile.getName());
        userProfileDtoIMPL.setUsername(userProfile.getUsername());
        userProfileDtoIMPL.setEmail(userProfile.getEmail());
        userProfileDtoIMPL.setActive(userProfile.isActive());


        return userProfileDtoIMPL;
    }


}
