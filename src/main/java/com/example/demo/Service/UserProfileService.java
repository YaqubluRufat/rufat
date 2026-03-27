package com.example.demo.Service;

import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.DTO.UserProfileDtoIMPL;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.UserProfile;
import com.example.demo.Exception.DepartmentNotFoundException;
import com.example.demo.Exception.UserProfileAlreadyExistsException;
import com.example.demo.Exception.UserProfileNotFoundException;
import com.example.demo.Mapper.UserProfileMapper;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.UserProfileRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserProfileService {
    @Autowired
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final DepartmentRepository departmentRepository;


    public UserProfileService(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper, DepartmentRepository departmentRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
        this.departmentRepository = departmentRepository;
    }

    public UserProfileDtoIMPL addUserprofile(UserProfileDTO userProfileDTO) {
        String email = userProfileDTO.getEmail();
        if (userProfileRepository.findByEmail(email).isPresent()) {
            throw new UserProfileAlreadyExistsException("This profile already exists.");
        } else {

            UserProfile userProfile1 = userProfileMapper.touserProfile(userProfileDTO);
            UserProfile save = userProfileRepository.save(userProfile1);
            return userProfileMapper.toUserProfileDTOIMPL(save);

        }
    }

    public UserProfileDtoIMPL findById(Long id) {
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() ->
                new UserProfileNotFoundException("The UserProfile was not found"));
        return userProfileMapper.toUserProfileDTOIMPL(userProfile);

    }
    public UserProfileDtoIMPL updateUserProfile(Long id,UserProfileDTO userProfileDTO){
        UserProfile userProfile = userProfileRepository.findById(id).orElseThrow(() ->
                new UserProfileNotFoundException("The UserProfile was not found"));
        userProfile.setName(userProfileDTO.getName());
        userProfile.setUsername(userProfileDTO.getUsername());
        userProfile.setEmail(userProfileDTO.getEmail());
        userProfile.setActive(userProfileDTO.isActive());

        UserProfile save = userProfileRepository.save(userProfile);
        return userProfileMapper.toUserProfileDTOIMPL(save);


    }
    public  void deleteUserProfile(Long id){
        userProfileRepository.deleteById(id);
    }
}
