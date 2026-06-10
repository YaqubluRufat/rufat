package com.example.demo.ServiceKafka.User;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducerEvent {

    private final KafkaTemplate<String,Object>kafkaTemplate;

    public UserProducerEvent(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void onPublishSavedUser(UserSavedEvent event){

        kafkaTemplate.send("user-saved",
                event
        );
    }
    public void onPublishDeleteUser(UserDeleteEvent event){

        kafkaTemplate.send("user-deleted",
                event
        );
    }
    public void onPublishUpdate(UserUpdatedEvent event){

        kafkaTemplate.send("user-updated",
                event
        );
    }


}
