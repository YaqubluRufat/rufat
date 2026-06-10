package com.example.demo.ServiceKafka.Product;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductProducerService {

    private final KafkaTemplate<String,Object>kafkaTemplate;

    public ProductProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void onPublishedSavedProduct(ProductSavedEvent event){

        kafkaTemplate.send("product-saved",
                event
        );
    }
    public void onPublishDeleteProduct(ProductDeletedEvent event){

        kafkaTemplate.send("product-deleted",
                event
        );
    }
    public void onPublishUpdateProduct(ProductUpdatedEvent event){

        kafkaTemplate.send("product-updated",
                event
        );
    }


}
