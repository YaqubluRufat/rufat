package com.example.demo.Mapper;

import com.example.demo.Dto.UserDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser (UserDto userDto);

    UserDtoIMPL toDtoIMPL (User user);
}
