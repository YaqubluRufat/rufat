package com.example.demo.Mapper;

import com.example.demo.DTO.DepartmentDto;
import com.example.demo.DTO.DepartmentDtoIMPL;
import com.example.demo.DTO.UserDtoIMPL;
import com.example.demo.DTO.UserProfileDtoIMPL;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserProfile;
import com.example.demo.Repository.UserProfileRepository;
import com.example.demo.Repository.UserRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartmentMapper {
    @Autowired
    private final UserProfileRepository userProfileRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public DepartmentMapper(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper, UserMapper userMapper, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userMapper = userMapper;

        this.userRepository = userRepository;
    }

    public Department toDepartment(DepartmentDto departmentDto) {
        Department department = new Department();

        department.setName(departmentDto.getName());
        if(departmentDto.getUserId()!=null){
        List<User> users = departmentDto.getUserId().stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found: " + id)))
                .toList();
        users.forEach(p->p.setDepartment(department));

        department.setUserList(users);}

        return department;
    }

    public DepartmentDtoIMPL toDepartmentIMPL(Department department) {
        DepartmentDtoIMPL departmentDtoIMPL = new DepartmentDtoIMPL();
        departmentDtoIMPL.setId(department.getId());
        departmentDtoIMPL.setName(department.getName());
        if(department.getUserList()!=null) {
            List<UserDtoIMPL> list = department.getUserList().stream().map(p -> {


                UserDtoIMPL userDtoIMPL = new UserDtoIMPL();
                userDtoIMPL.setId(p.getId());
                userDtoIMPL.setName(p.getName());
                if (p.getUserProfile() != null) {
                    UserProfileDtoIMPL userProfileDtoIMPL = new UserProfileDtoIMPL();
                    userProfileDtoIMPL.setUserProfileId(p.getUserProfile().getId());
                    userProfileDtoIMPL.setName(p.getUserProfile().getName());
                    userProfileDtoIMPL.setEmail(p.getUserProfile().getEmail());
                    userProfileDtoIMPL.setActive(p.getUserProfile().isActive());
                    userProfileDtoIMPL.setUsername(p.getUserProfile().getUsername());

                    userDtoIMPL.setUserProfile(userProfileDtoIMPL);
                }

                return userDtoIMPL;
            }).toList();
            departmentDtoIMPL.setUsers(list);
        }
        return  departmentDtoIMPL;

    }
    }
