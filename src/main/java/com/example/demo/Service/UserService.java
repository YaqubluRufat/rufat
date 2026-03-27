package com.example.demo.Service;

import com.example.demo.DTO.UserDto;
import com.example.demo.DTO.UserDtoIMPL;
import com.example.demo.DTO.UserProfileDtoIMPL;
import com.example.demo.Entity.User;
import com.example.demo.Entity.UserProfile;
import com.example.demo.Exception.UserProfileNotFoundException;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.UserProfileRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserProfileRepository userProfileRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, UserProfileService userProfileService, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;

        this.userProfileRepository = userProfileRepository;
    }

    public UserDtoIMPL addUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
if(userDto.getUserProfileId()!=null){
        UserProfile userProfile = userProfileRepository.findById(userDto.getUserProfileId()).orElseThrow(() ->
                new UserProfileNotFoundException("Userprofile not found"));
        userProfile.setUser(user);
        user.setUserProfile(userProfile);
}

        User save = userRepository.save(user);

        return userMapper.userDtoIMPL(save);

    }
}
