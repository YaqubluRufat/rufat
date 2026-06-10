package com.example.demo.ServiceKafka.User;

import java.time.LocalDateTime;

public record UserDeleteEvent(
        Long id,
        String username,
        String password,
        LocalDateTime deleteTime
) {
}
