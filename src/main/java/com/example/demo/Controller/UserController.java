package com.example.demo.Controller;

import com.example.demo.DTO.UserDto;
import com.example.demo.DTO.UserDtoIMPL;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/save")
    public ResponseEntity<UserDtoIMPL>addUser(@RequestBody UserDto userDto){
        UserDtoIMPL userDtoIMPL = userService.addUser(userDto);
        return ResponseEntity.ok().body(userDtoIMPL);
    }
}
