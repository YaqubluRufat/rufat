package com.example.demo.ServiceKafka.Department;

import java.time.LocalDateTime;

public record DepartmentDeletedEvent(
        Long id,
        String name,
        LocalDateTime deleteTime
) {
}
