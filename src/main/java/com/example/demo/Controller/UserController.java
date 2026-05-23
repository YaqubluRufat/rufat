package com.example.demo.Controller;

import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<UserDtoIMPL> findById(@PathVariable Long id){
        UserDtoIMPL byId = userService.findById(id);
        return ResponseEntity.ok(byId);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User is enabled");
    }

}
