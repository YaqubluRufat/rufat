package com.example.demo.Mapper;

import com.example.demo.Dto.RoleDto;
import com.example.demo.Entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toRole(RoleDto roleDto);



}
