package com.example.demo.Service;

import com.example.demo.DTO.DepartmentDto;
import com.example.demo.DTO.DepartmentDtoIMPL;
import com.example.demo.Entity.Department;
import com.example.demo.Exception.DepartmentAlreadyExistsException;
import com.example.demo.Exception.DepartmentNotFoundException;
import com.example.demo.Mapper.DepartmentMapper;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@org.springframework.stereotype.Service

public class DepartmentService {
    @Autowired
    private final DepartmentMapper departmentMapper;
    @Autowired
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;


    public DepartmentService(DepartmentMapper departmentMapper, DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.departmentMapper = departmentMapper;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;

    }

    public DepartmentDtoIMPL addDepartment(DepartmentDto departmentRequest) {
        if (departmentRepository.findByName(departmentRequest.getName()).isPresent()) {
            throw new DepartmentAlreadyExistsException("This department already exists");
        } else {

            Department department = departmentMapper.toDepartment(departmentRequest);

            Department save = departmentRepository.save(department);
            return departmentMapper.toDepartmentIMPL(save);
        }
    }

    public DepartmentDtoIMPL findByDepartmentName(String name) {
        Department department = departmentRepository.findByName(name).orElseThrow(() -> new
                DepartmentNotFoundException("The department was not found"));
        return departmentMapper.toDepartmentIMPL(department);
    }

    public DepartmentDtoIMPL findById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() ->
                new DepartmentNotFoundException("The department was not found"));
        return departmentMapper.toDepartmentIMPL(department);
    }

    public DepartmentDtoIMPL updateDeparmetn(Long id, DepartmentDto departmentDto) {
        Department department = departmentRepository.findById(id).orElseThrow(() ->
                new DepartmentNotFoundException("The department was not found"));
        department.setName(departmentDto.getName());

        return departmentMapper.toDepartmentIMPL(department);

    }

    public void deleteById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() ->
                new DepartmentNotFoundException("Department not found"));


        departmentRepository.delete(department);
    }

    public Long countAllDepartments(Long id) {
        if (departmentRepository.findById(id).isEmpty()) {
            throw new DepartmentNotFoundException("The department was not found");
        } else {
            return departmentRepository.couldAllDepartments(id);

        }
    }
    public Page<DepartmentDtoIMPL> findAllBytId(Long id , int page, int size,
                                                String sortBy,
                                                String sortDir ){

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return departmentRepository.findAllById(id,pageRequest).map(departmentMapper::toDepartmentIMPL);
    }
}

