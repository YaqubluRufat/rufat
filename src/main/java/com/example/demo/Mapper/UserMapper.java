package com.example.demo.Mapper;

import com.example.demo.Dto.UserDto;
import com.example.demo.Dto.UserDtoIMPL;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

     User toUser (UserDto userDto);
     @Mapping(source = "roles",target = "role",qualifiedByName = "mapToRole")
     UserDtoIMPL toUserDtoIMPL (User user);
@Named("mapToRole")
     default String mapToRole(Set<Role>roles){
         if(roles==null) return null;

        return roles.stream().map(Role::getName).collect(Collectors.joining(","));


     }
}
