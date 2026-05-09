package com.example.demo.Controller;

import com.example.demo.Dto.ChangedPasswordDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Service.UserService;
import org.hibernate.id.factory.IdentifierGeneratorFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<UserDtoIMPL>findById(@PathVariable Long id){
        UserDtoIMPL byId = userService.findById(id);
        return ResponseEntity.ok(byId);
    }


    @GetMapping("/find")
    public ResponseEntity<UserDtoIMPL>findMyUser(
            Authentication authentication){
        UserDtoIMPL myUser = userService.findMyUser(authentication);
        return ResponseEntity.ok(myUser);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?>deleteById(Authentication authentication){
        userService.deleteMyUser(authentication);
        return ResponseEntity.ok("Deleted successfully");
    }
    @PutMapping("/update")
    public ResponseEntity<UserDtoIMPL>updateMyUser(@RequestBody ChangedPasswordDto changedPasswordDto,Authentication authentication){
        UserDtoIMPL userDtoIMPL = userService.updateMyUser(changedPasswordDto,authentication);
        return ResponseEntity.ok(userDtoIMPL);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?>updateRole(@PathVariable Long id){
        userService.updateRole(id);
        return ResponseEntity.ok("Updated successfully");
    }
    @GetMapping("/list")
    public ResponseEntity<Page<UserDtoIMPL>>findAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy,
                                             @RequestParam(defaultValue = "asc") String sortDir){
        Page<UserDtoIMPL> all = userService.findAll(page, size, sortBy, sortDir);
        return ResponseEntity.ok(all);

    }

}
