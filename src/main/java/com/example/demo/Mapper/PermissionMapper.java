package com.example.demo.Mapper;

import com.example.demo.Dto.PermissionDto;
import com.example.demo.Entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionDto permissionDto);
}
