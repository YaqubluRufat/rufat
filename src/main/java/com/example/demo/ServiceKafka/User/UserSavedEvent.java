package com.example.demo.ServiceKafka.User;

import java.time.LocalDateTime;

public record UserSavedEvent(
        Long id,
        String username,
        String password,
        LocalDateTime savedTime
) {
}
