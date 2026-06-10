package com.example.demo.ServiceKafka.Department;

import java.time.LocalDateTime;

public record DepartmentUpdatedEvent(
        Long id,
        String name,
        LocalDateTime updatedTime
) {
}
