package com.example.demo.ServiceKafka.Market;

import java.time.LocalDateTime;

public record MarketDeleteEvent(
        Long id,
        String name,
        String location,
        LocalDateTime deletedTime
) {
}
