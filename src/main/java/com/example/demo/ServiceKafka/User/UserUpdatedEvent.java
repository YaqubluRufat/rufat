package com.example.demo.ServiceKafka.User;

import java.time.LocalDateTime;

public record UserUpdatedEvent(
        Long id,
        String username,
        String password,
        LocalDateTime updatedTime
) {
}
