package com.example.demo.Service;

import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Dto.DepartmentDtoIMPL;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.Market;
import com.example.demo.Mapper.DepartmentMapper;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.MarketRepository;
import com.example.demo.ServiceKafka.Department.DepartmentDeletedEvent;
import com.example.demo.ServiceKafka.Department.DepartmentProducerEvent;
import com.example.demo.ServiceKafka.Department.DepartmentSavedEvent;
import com.example.demo.ServiceKafka.Department.DepartmentUpdatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    DepartmentMapper departmentMapper;
    @Mock
    DepartmentRepository departmentRepository;
    @InjectMocks
    DepartmentService departmentService;
    @Mock
    MarketRepository marketRepository;
    @Mock
    DepartmentProducerEvent departmentProducerEvent;


    @Test
    void addDepartment() {
        Department department = new Department();

        Market market = new Market();
        market.setId(1L);

        DepartmentDto dto = new DepartmentDto();
        dto.setMarketId(1L);

        Department savedDepartment = new Department();
        savedDepartment.setId(1L);
        savedDepartment.setName("Rufat");

        DepartmentDtoIMPL departmentDtoIMPL = new DepartmentDtoIMPL();


        when(marketRepository.findById(1L)).thenReturn(Optional.of(market));
        when(departmentMapper.toDepartment(dto)).thenReturn(department);
        when(departmentRepository.save(department)).thenReturn(savedDepartment);
        when(departmentMapper.toDepartmentDtoIMPL(savedDepartment)).thenReturn(departmentDtoIMPL);
        doNothing().when(departmentProducerEvent).publishDepartmentSavedEvent(any(DepartmentSavedEvent.class));


        DepartmentDtoIMPL departmentDtoIMPL1 = departmentService.addDepartment(dto);

        assertEquals(departmentDtoIMPL1, departmentDtoIMPL);

        verify(marketRepository).findById(1L);
        verify(departmentMapper).toDepartment(dto);
    }


    @Test
    void findById() {
        Department department = new Department();
        department.setId(1L);

        DepartmentDtoIMPL departmentDtoIMPL = new DepartmentDtoIMPL();

        when(departmentRepository.findDepartmentById(1L)).thenReturn(Optional.of(department));
        when(departmentMapper.toDepartmentDtoIMPL(department)).thenReturn(departmentDtoIMPL);

        DepartmentDtoIMPL byId = departmentService.findById(1L);

        assertEquals(byId, departmentDtoIMPL);

        verify(departmentRepository).findDepartmentById(1L);
    }

    @Test
    void deleteById() {
        Department department = new Department();
        department.setId(1L);
        department.setName("Rufat");


        when(departmentRepository.findDepartmentById(1L)).thenReturn(Optional.of(department));
        doNothing().when(departmentProducerEvent)
                .publishDepartmentDeleteEvent(any(DepartmentDeletedEvent.class));


        departmentService.deleteById(1L);

        verify(departmentRepository).findDepartmentById(1L);

    }

    @Test
    void updateById() {
        Department department = new Department();
        department.setId(1L);

        Market market = new Market();
        market.setId(1L);

        DepartmentDto dto = new DepartmentDto();
        dto.setMarketId(1L);

        Department savedDepartment = new Department();
        savedDepartment.setId(1L);
        savedDepartment.setName("Rufat");

        DepartmentDtoIMPL departmentDtoIMPL = new DepartmentDtoIMPL();


        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(marketRepository.findMarketById(1L)).thenReturn(Optional.of(market));
        when(departmentRepository.save(department)).thenReturn(savedDepartment);
        when(departmentMapper.toDepartmentDtoIMPL(savedDepartment)).thenReturn(departmentDtoIMPL);
        doNothing().when(departmentProducerEvent).publishDepartmentUpdatedEvent(any(DepartmentUpdatedEvent.class));

        DepartmentDtoIMPL departmentDtoIMPL1 = departmentService.updateById(1L, dto);

        assertEquals(departmentDtoIMPL, departmentDtoIMPL1);
        verify(departmentRepository).findById(1L);
        verify(marketRepository).findMarketById(1L);

    }

}