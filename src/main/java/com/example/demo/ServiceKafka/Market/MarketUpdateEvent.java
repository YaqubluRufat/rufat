package com.example.demo.ServiceKafka.Market;

import java.time.LocalDateTime;

public record MarketUpdateEvent(
        Long id,
        String name,
        String location,
        LocalDateTime updatedTime
) {
}
