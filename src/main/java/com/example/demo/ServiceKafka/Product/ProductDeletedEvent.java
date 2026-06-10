package com.example.demo.ServiceKafka.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDeletedEvent(
        Long id,
        String name,
        BigDecimal price,
        LocalDateTime deletedTime
) {
}
