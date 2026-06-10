package com.example.demo.ServiceKafka.Department;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DepartmentProducerEvent {

    private final KafkaTemplate<String,Object>kafkaTemplate;

    public DepartmentProducerEvent(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void publishDepartmentSavedEvent(DepartmentSavedEvent event){

        kafkaTemplate.send("department-saved",
                event
        );
    }
    public  void publishDepartmentDeleteEvent(DepartmentDeletedEvent event){

        kafkaTemplate.send("department-deleted",
                event
        );
    }
    public void publishDepartmentUpdatedEvent(DepartmentUpdatedEvent event){

        kafkaTemplate.send("department-updated",
                event
        );
    }


}
