package com.example.demo.Mapper;

import com.example.demo.DTO.DepartmentDtoIMPL;
import com.example.demo.DTO.UserDto;
import com.example.demo.DTO.UserDtoIMPL;
import com.example.demo.DTO.UserProfileDtoIMPL;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserProfile;
import com.example.demo.Exception.DepartmentNotFoundException;
import com.example.demo.Exception.UserProfileNotFoundException;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.UserProfileRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    private final DepartmentRepository departmentRepository;
    private final UserProfileRepository userProfileRepository;

    public UserMapper(DepartmentRepository departmentRepository, UserProfileRepository userProfileRepository) {
        this.departmentRepository = departmentRepository;
        this.userProfileRepository = userProfileRepository;
    }

    public User toUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());


        return user;


    }

    public UserDtoIMPL userDtoIMPL(User user) {
        UserDtoIMPL userDtoIMPL = new UserDtoIMPL();
        userDtoIMPL.setId(user.getId());
        userDtoIMPL.setName(user.getName());
        if (user.getUserProfile() != null) {
            UserProfileDtoIMPL userProfileDtoIMPL = new UserProfileDtoIMPL();
            userProfileDtoIMPL.setUserProfileId(user.getUserProfile().getId());
            userProfileDtoIMPL.setName(user.getUserProfile().getName());
            userProfileDtoIMPL.setUsername(user.getUserProfile().getUsername());
            userProfileDtoIMPL.setEmail(user.getUserProfile().getEmail());
            userProfileDtoIMPL.setActive(user.getUserProfile().isActive());
            userDtoIMPL.setUserProfile(userProfileDtoIMPL);
        }
        return userDtoIMPL;


    }

    public List<UserDtoIMPL> userDtoListIMPL(List<User> users) {
        List<UserDtoIMPL> list = users.stream().map(this::userDtoIMPL).toList();
        return list;

    }


}
