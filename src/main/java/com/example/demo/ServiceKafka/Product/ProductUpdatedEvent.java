package com.example.demo.ServiceKafka.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductUpdatedEvent(
        Long id,
        String name,
        BigDecimal price,
        LocalDateTime updateTime
) {
}
