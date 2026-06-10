package com.example.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketDtoIMPL {
    private Long id;
    private String name;
    private String location;
    private List<DepartmentDtoIMPL>department;
    private LocalDateTime createdData;
    private LocalDateTime updatedDate;


}
