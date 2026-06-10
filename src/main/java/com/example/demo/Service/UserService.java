package com.example.demo.Service;

import com.example.demo.Dto.ChangedDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.User;
import com.example.demo.Exception.IncorrectPassword;
import com.example.demo.Exception.InvalidSort;
import com.example.demo.Exception.UserNotFound;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Repository.UserRepository;
import com.example.demo.ServiceKafka.User.UserDeleteEvent;
import com.example.demo.ServiceKafka.User.UserProducerEvent;
import com.example.demo.ServiceKafka.User.UserUpdatedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserProducerEvent userProducerEvent;


    public UserService(UserRepository userRepository, UserMapper userMapper,
                       PasswordEncoder passwordEncoder, UserProducerEvent userProducerEvent) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;

        this.userProducerEvent = userProducerEvent;
    }
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public UserDtoIMPL findById( Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));

        return userMapper.toUserDtoIMPL(user);
    }
    @Transactional
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        UserDeleteEvent userDeleteEvent = new UserDeleteEvent(user.getId(),user.getUsername(),user.getPassword(), LocalDateTime.now());
        userProducerEvent.onPublishDeleteUser(userDeleteEvent);
        userRepository.delete(user);

    }
    @Transactional
    @PreAuthorize(("hasRole('ADMIN') or #id == authentication.principal.id"))
    public UserDtoIMPL updateUser(Long id, ChangedDto changedDto){
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        if(!passwordEncoder.matches(changedDto.getGetOldPassword(),user.getPassword())){
            throw new IncorrectPassword("Incorrect Password");
        }
        user.setPassword(passwordEncoder.encode(changedDto.getGetNewPassword()));
        UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent(user.getId(),user.getUsername(),user.getPassword(),LocalDateTime.now());
        userProducerEvent.onPublishUpdate(userUpdatedEvent);
        return userMapper.toUserDtoIMPL(user);
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
