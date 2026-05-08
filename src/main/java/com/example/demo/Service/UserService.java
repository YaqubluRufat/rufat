package com.example.demo.Service;

import com.example.demo.Dto.ChangedPasswordDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.Exception.InvalidPasswordException;
import com.example.demo.Exception.InvalidSort;
import com.example.demo.Exception.UserNotFound;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @PreAuthorize("hasRole('ADMIN')")
    public UserDtoIMPL findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        return userMapper.toUserDtoIMPL(user);
    }

    @PreAuthorize("hasRole('USER')")
    public UserDtoIMPL findMyUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        return userMapper.toUserDtoIMPL(user);
    }

    @PreAuthorize("hasRole('USER')")
    public void deleteMyUser(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found"));
        userRepository.delete(user);
    }

    @PreAuthorize("hasRole('USER')")
    public UserDtoIMPL updateMyUser(ChangedPasswordDto changedPasswordDto,Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if (!passwordEncoder.matches(changedPasswordDto.getGetOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(changedPasswordDto.getGetNewPassword()));
        User save = userRepository.save(user);
        return userMapper.toUserDtoIMPL(save);

    }


    @PreAuthorize("hasRole('ADMIN')")
    public void updateRole(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        user.setRole(Role.OWNER);
        userRepository.save(user);

    }

    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDtoIMPL> findAll(int page, int size, String sortBy, String sortDir) {
        List<String> strings1 = List.of("id", "username");
        if (!strings1.contains(sortBy)) {
            throw new InvalidSort("Sort is incorrect");
        }
        List<String> strings = List.of("asc", "desc");
        if (!strings.contains(sortDir)) {
            throw new InvalidSort("Sort is incorrect");
        }
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageRequest).map(userMapper::toUserDtoIMPL);


    }
}
