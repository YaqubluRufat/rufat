package com.example.demo.Service;

import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.User;
import com.example.demo.Exception.InvalidSort;
import com.example.demo.Exception.UserNotFound;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserDtoIMPL findById( Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        return userMapper.toUserDtoIMPL(user);
    }
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        user.setEnabled(false);
        userRepository.save(user);

    }
    @PreAuthorize("hasRole('ADMIN')")
    public Page<UserDtoIMPL> getAll(int page,int size,String sortBy,String sortDir){
        List<String> strings = List.of("id", "username");
       if(!strings.contains(sortBy)){
            throw  new InvalidSort("Sort is invalid");
        }
        List<String> asc = List.of("asc", "desc");
        if(!asc.contains(sortDir)){
            throw new InvalidSort("Incorrect sort");
        }
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageRequest).map(userMapper::toUserDtoIMPL);

    }
}
