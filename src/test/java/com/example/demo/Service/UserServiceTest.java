package com.example.demo.Service;

import com.example.demo.Dto.ChangedDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.User;
import com.example.demo.Exception.IncorrectPassword;
import com.example.demo.Exception.UserNotFound;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ServiceKafka.User.UserDeleteEvent;
import com.example.demo.ServiceKafka.User.UserProducerEvent;
import com.example.demo.ServiceKafka.User.UserUpdatedEvent;
import org.h2.command.ddl.DropUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.postgresql.hostchooser.HostRequirement.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    UserService userService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    UserProducerEvent userProducerEvent;
    @Test
    void findById() {
        User user  =new User();
        user.setId(1L);

        UserDtoIMPL userDtoIMPL  =new UserDtoIMPL();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toUserDtoIMPL(user)).thenReturn(userDtoIMPL);

        UserDtoIMPL byId = userService.findById(1L);

        assertEquals(byId,userDtoIMPL);

        verify(userRepository).findById(1L);


    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userProducerEvent).onPublishDeleteUser(any(UserDeleteEvent.class));

        userService.deleteUser(1L);

        verify(userRepository).findById(1L);

    }

    @Test
    void updateUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("rufat");
        user.setPassword("encodedOldPassword");

        ChangedDto changedDto = new ChangedDto();
        changedDto.setGetOldPassword("oldPassword");
        changedDto.setGetNewPassword("newPassword");

        UserDtoIMPL userDtoIMPL = new UserDtoIMPL();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("oldPassword", "encodedOldPassword")).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        doNothing().when(userProducerEvent).onPublishUpdate(any(UserUpdatedEvent.class));
        when(userMapper.toUserDtoIMPL(user)).thenReturn(userDtoIMPL);

        UserDtoIMPL userDtoIMPL1 =
                userService.updateUser(1L, changedDto);

        assertEquals(userDtoIMPL1,userDtoIMPL);

        verify(userRepository).findById(1L);



    }


    }
