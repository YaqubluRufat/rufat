package com.example.demo.ServiceKafka.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductSavedEvent(
        Long id,
        String name,
        BigDecimal price,
        LocalDateTime  savedTime
) {
}
