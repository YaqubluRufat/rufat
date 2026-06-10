package com.example.demo.Mapper;

import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Dto.DepartmentDtoIMPL;
import com.example.demo.Entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {ProductMapper.class})
public interface DepartmentMapper {
    @Mapping(source = "marketId",target = "market.id")
    Department toDepartment(DepartmentDto dto);

    DepartmentDtoIMPL toDepartmentDtoIMPL(Department department);
}
