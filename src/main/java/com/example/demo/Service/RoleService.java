package com.example.demo.Service;

import com.example.demo.Dto.RoleDto;
import com.example.demo.Entity.Permission;
import com.example.demo.Entity.Role;
import com.example.demo.Exception.PermissionNotFound;
import com.example.demo.Exception.RoleAlreadyExists;
import com.example.demo.Exception.RoleNotFound;
import com.example.demo.Mapper.RoleMapper;
import com.example.demo.Repository.PermissionRepository;
import com.example.demo.Repository.RoleRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.permissionRepository = permissionRepository;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public Role addRole(RoleDto roleDto) {
        Optional<Role> byName = roleRepository.findByName(roleDto.getName());
        if (byName.isPresent()) {
            throw new RoleAlreadyExists("Role already exists");
        }
        Role role = roleMapper.toRole(roleDto);
        Role save = roleRepository.save(role);
        return save;

    }
    @PreAuthorize("hasRole('ADMIN')")
    public void addRolePermission(Long roleId, Long permissionId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RoleNotFound("Role not found"));
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(() ->
                new PermissionNotFound("Permission not found"));
        role.getPermission().add(permission);
        roleRepository.save(role);

    }
    @PreAuthorize("hasRole('ADMIN')")
    public Role findById(Long id){
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFound("Role not found"));
        return role;
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id){
        Role role = roleRepository.findById(id).orElseThrow(() -> new RoleNotFound("Role not found"));
        roleRepository.delete(role);
    }
}

