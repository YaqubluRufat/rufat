package com.example.demo.ServiceKafka.Market;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MarketProducerEvent {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MarketProducerEvent(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishMarketDelete(MarketDeleteEvent event) {

        kafkaTemplate.send(
                "market-deleted",
                event
        );
    }
    public void publishMarketUpdate(MarketUpdateEvent event){

        kafkaTemplate.send("market-updated",
                event
        );
    }
    public void publishMarketSaved(MarketSavedEvent event){

        kafkaTemplate.send("market-saved",
                event
        );
    }
}
