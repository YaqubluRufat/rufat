package com.example.demo.Service;

import com.example.demo.Dto.ChangedDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.User;
import com.example.demo.Exception.IncorrectPassword;
import com.example.demo.Exception.UserNotFound;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserDtoIMPL findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        return userMapper.toDtoIMPL(user);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        userRepository.delete(user);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserDtoIMPL updatePassword(Long id, ChangedDto changedDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        if (!passwordEncoder.matches(changedDto.getOldPassword(), user.getPassword())) {
            throw new IncorrectPassword("Incorrect password");
        }

        user.setPassword(passwordEncoder.encode(changedDto.getNewPassword()));

        return userMapper.toDtoIMPL(user);

    }

}
