package com.example.demo.Mapper;

import com.example.demo.Dto.UserDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser (UserDto userDto);

    UserDtoIMPL toUserDtoIMPL (User user);

    List<UserDtoIMPL> toDTO_USER_LIST(List<User>users);
}
