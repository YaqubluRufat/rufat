package com.example.demo.Service;

import com.example.demo.Dto.PermissionDto;
import com.example.demo.Entity.Permission;
import com.example.demo.Entity.Role;
import com.example.demo.Exception.PermissionAlreadyExists;
import com.example.demo.Exception.PermissionNotFound;
import com.example.demo.Exception.RoleNotFound;
import com.example.demo.Mapper.PermissionMapper;
import com.example.demo.Repository.PermissionRepository;
import com.example.demo.Repository.RoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionService {

    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionMapper permissionMapper, PermissionRepository permissionRepository) {
        this.permissionMapper = permissionMapper;

        this.permissionRepository = permissionRepository;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Permission addPermission(PermissionDto permissionDto) {
        Optional<Permission> byName = permissionRepository.findByName(permissionDto.getName());
        if (byName.isPresent()) {
            throw new PermissionAlreadyExists("Permission already exists");
        }
        Permission permission = permissionMapper.toPermission(permissionDto);
        Permission save = permissionRepository.save(permission);
        return save;

    }
    @PreAuthorize("hasRole('ADMIN')")
    public Permission findById(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() ->
                new PermissionNotFound("Permission not found"));
        return permission;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() ->
                new PermissionNotFound("Permission not found"));
        permissionRepository.delete(permission);
    }
}
