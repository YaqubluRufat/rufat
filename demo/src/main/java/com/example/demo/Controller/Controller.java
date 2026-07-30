package com.example.demo.Controller;

import com.example.demo.Dto.ChangedDto;
import com.example.demo.Dto.LoginDto;
import com.example.demo.Dto.TokenDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Security.RegisterService;
import com.example.demo.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class Controller {
    private final UserService userService;
    private final RegisterService registerService;

    public Controller(UserService userService, RegisterService registerService) {
        this.userService = userService;
        this.registerService = registerService;
    }
    @PostMapping("/save")
    public ResponseEntity<UserDtoIMPL>register(@RequestBody LoginDto loginDto){
        UserDtoIMPL register = registerService.register(loginDto);
        return ResponseEntity.ok().body(register);
    }
    @PostMapping("/login")
    public ResponseEntity<TokenDto>login(@RequestBody LoginDto loginDto){
        TokenDto login = registerService.login(loginDto);
        return ResponseEntity.ok().body(login);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<UserDtoIMPL>findById(@PathVariable Long id){
        UserDtoIMPL byId = userService.findById(id);
        return ResponseEntity.ok().body(byId);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok().body("Deleted");
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDtoIMPL>updateUser(@PathVariable Long id, @RequestBody ChangedDto changedDto){
        UserDtoIMPL userDtoIMPL = userService.updatePassword(id, changedDto);
        return ResponseEntity.ok().body(userDtoIMPL);
    }

}
