package com.example.demo.ServiceKafka.Department;

import java.time.LocalDateTime;

public record DepartmentSavedEvent(
        Long id,
        String name,
        LocalDateTime savedTime
) {
}
