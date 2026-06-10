package com.example.demo.ServiceKafka.Market;

import java.time.LocalDateTime;

public record MarketSavedEvent(
        Long id,
        String name,
        String location,
        LocalDateTime savedTime
) {
}
