package com.example.demo.Controller;

import com.example.demo.DTO.UserProfileDTO;
import com.example.demo.DTO.UserProfileDtoIMPL;
import com.example.demo.Service.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api1")
public class UserProfile {

    private final UserProfileService userProfileService;

    public UserProfile(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }
    @PostMapping("/save")
    public ResponseEntity<UserProfileDtoIMPL>addProfile(@RequestBody@Valid UserProfileDTO userProfileDTO){
        UserProfileDtoIMPL userProfileDtoIMPL = userProfileService.addUserprofile(userProfileDTO);
        return ResponseEntity.ok().body(userProfileDtoIMPL);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<UserProfileDtoIMPL>findById(@PathVariable Long id){
        UserProfileDtoIMPL byId = userProfileService.findById(id);
        return ResponseEntity.ok().body(byId);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<UserProfileDtoIMPL>updateUserProfile(@PathVariable Long id,@RequestBody UserProfileDTO userProfileDTO){
        UserProfileDtoIMPL userProfileDtoIMPL = userProfileService.updateUserProfile(id, userProfileDTO);
        return ResponseEntity.ok().body(userProfileDtoIMPL);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable Long id){
        userProfileService.deleteUserProfile(id);
        return ResponseEntity.badRequest().body("Deleted Succesfully");

    }




}
