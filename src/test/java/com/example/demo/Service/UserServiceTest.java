package com.example.demo.Service;

import com.example.demo.Dto.ChangedPasswordDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.User;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserService userService;
    @Mock
    Authentication authentication;
    @Mock
    PasswordEncoder passwordEncoder;


    @Test
    void findById() {
        User user = new User();
        user.setId(1L);
        UserDtoIMPL userDtoIMPL = new UserDtoIMPL();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserDtoIMPL(user)).thenReturn(userDtoIMPL);

        UserDtoIMPL byId = userService.findById(1L);

        assertEquals(userDtoIMPL,byId);

        verify(userRepository).findById(1L);
        verify(userMapper).toUserDtoIMPL(user);

    }

    @Test
    void findMyUser() {
        User user = new User();
        user.setUsername("rufat");

        UserDtoIMPL userDtoIMPL = new UserDtoIMPL();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(userMapper.toUserDtoIMPL(user)).thenReturn(userDtoIMPL);

        UserDtoIMPL myUser = userService.findMyUser(authentication);

        assertEquals(userDtoIMPL,myUser);

        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");


    }

    @Test
    void deleteMyUser() {
        User user = new User();
        user.setUsername("rufat");

        UserDtoIMPL userDtoIMPL = new UserDtoIMPL();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));


        userService.deleteMyUser(authentication);

        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");

    }

    @Test
    void updateMyUser() {
        User user = new User();
        user.setPassword("encoded1234");
        user.setUsername("rufat");

        UserDtoIMPL userDtoIMPL = new UserDtoIMPL();

        ChangedPasswordDto changedPasswordDto = new ChangedPasswordDto();
        changedPasswordDto.setGetNewPassword("123");
        changedPasswordDto.setGetOldPassword("1234");

        User saved = new User();

        when(authentication.getName()).thenReturn("rufat");
        when(userRepository.findByUsername("rufat")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("1234", "encoded1234")).thenReturn(true);
        when(passwordEncoder.encode("123")).thenReturn("encoded123");
        when(userRepository.save(user)).thenReturn(saved);
        when(userMapper.toUserDtoIMPL(saved)).thenReturn(userDtoIMPL);

        UserDtoIMPL userDtoIMPL1 = userService.updateMyUser(changedPasswordDto,authentication);

        assertEquals(userDtoIMPL,userDtoIMPL1);

        verify(authentication).getName();
        verify(userRepository).findByUsername("rufat");
        verify(userRepository).save(user);
    }

    @Test
    void updateRole() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateRole(1L);

        verify(userRepository).findById(1L);

    }
}