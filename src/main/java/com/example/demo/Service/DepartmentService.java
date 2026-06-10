package com.example.demo.Service;

import com.example.demo.Dto.DepartmentDto;
import com.example.demo.Dto.DepartmentDtoIMPL;
import com.example.demo.Entity.Department;
import com.example.demo.Entity.Market;
import com.example.demo.Exception.DepartmentNotFound;
import com.example.demo.Exception.InvalidSort;
import com.example.demo.Exception.MarketNotFound;
import com.example.demo.Mapper.DepartmentMapper;
import com.example.demo.Repository.DepartmentRepository;
import com.example.demo.Repository.MarketRepository;

import com.example.demo.ServiceKafka.Department.DepartmentDeletedEvent;
import com.example.demo.ServiceKafka.Department.DepartmentProducerEvent;
import com.example.demo.ServiceKafka.Department.DepartmentSavedEvent;
import com.example.demo.ServiceKafka.Department.DepartmentUpdatedEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;
    private final MarketRepository marketRepository;
    private final DepartmentProducerEvent departmentProducerEvent;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper, MarketRepository marketRepository, DepartmentProducerEvent departmentProducerEvent) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
        this.marketRepository = marketRepository;
        this.departmentProducerEvent = departmentProducerEvent;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @departmentSecurity.isOwner(#dto.marketId,authentication)")
    public DepartmentDtoIMPL addDepartment(DepartmentDto dto) {
        Market market = marketRepository.findById(dto.getMarketId()).orElseThrow(() -> new MarketNotFound("Market not found"));

        Department department = departmentMapper.toDepartment(dto);
        department.setMarket(market);
        Department save = departmentRepository.save(department);
        DepartmentSavedEvent departmentSavedEvent = new DepartmentSavedEvent(save.getId(), save.getName(),LocalDateTime.now());
        departmentProducerEvent.publishDepartmentSavedEvent(departmentSavedEvent);

        return departmentMapper.toDepartmentDtoIMPL(save);
    }

    @Transactional(readOnly = true)

    public DepartmentDtoIMPL findById(Long id) {
        Department department = departmentRepository.findDepartmentById(id).orElseThrow(() -> new DepartmentNotFound("Department not found"));
        return departmentMapper.toDepartmentDtoIMPL(department);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @departmentSecurity.isOWNER(#departmentId,authentication)")
    public void deleteById(Long departmentId) {
        Department department = departmentRepository.findDepartmentById(departmentId).orElseThrow(() ->
                new DepartmentNotFound("Department not found"));
        departmentRepository.delete(department);
        DepartmentDeletedEvent departmentDeletedEvent = new DepartmentDeletedEvent(department.getId(), department.getName(), LocalDateTime.now());
        departmentProducerEvent.publishDepartmentDeleteEvent(departmentDeletedEvent);

    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or @departmentSecurity.isOWNER(#departmentId,authentication) and @marketSecurity.isOwner(#dto.marketId,authentication)")
    public DepartmentDtoIMPL updateById(Long departmentId, DepartmentDto dto) {
        Department department = departmentRepository.findById(departmentId).orElseThrow(() -> new DepartmentNotFound("Department not found"));
        if (dto.getMarketId() != null) {
            Market market = marketRepository.findMarketById(dto.getMarketId()).orElseThrow(() -> new MarketNotFound("Market not found"));
            department.setMarket(market);
        }
        if (dto.getName() != null) {
            department.setName(dto.getName());
        }

        Department save = departmentRepository.save(department);
        DepartmentUpdatedEvent departmentUpdatedEvent = new DepartmentUpdatedEvent(save.getId(),save.getName(),LocalDateTime.now());
        departmentProducerEvent.publishDepartmentUpdatedEvent(departmentUpdatedEvent);
        return departmentMapper.toDepartmentDtoIMPL(save);
    }

    public Page<DepartmentDtoIMPL> getAll(int page, int size, String sortBy, String sortDir) {
        List<String> strings = List.of("id", "name");
        if (!strings.contains(sortBy)) {
            throw new InvalidSort("Sort is invalid");
        }
        List<String> asc = List.of("asc", "desc");
        if (!asc.contains(sortDir)) {
            throw new InvalidSort("Incorrect sort");
        }
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return departmentRepository.findAllBy(pageRequest).map(departmentMapper::toDepartmentDtoIMPL);
    }


}
