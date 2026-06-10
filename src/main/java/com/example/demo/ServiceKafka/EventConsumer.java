package com.example.demo.ServiceKafka;

import com.example.demo.ServiceKafka.Department.DepartmentDeletedEvent;
import com.example.demo.ServiceKafka.Department.DepartmentSavedEvent;
import com.example.demo.ServiceKafka.Department.DepartmentUpdatedEvent;
import com.example.demo.ServiceKafka.Market.MarketDeleteEvent;
import com.example.demo.ServiceKafka.Market.MarketSavedEvent;
import com.example.demo.ServiceKafka.Market.MarketUpdateEvent;
import com.example.demo.ServiceKafka.Product.ProductDeletedEvent;
import com.example.demo.ServiceKafka.Product.ProductSavedEvent;
import com.example.demo.ServiceKafka.Product.ProductUpdatedEvent;
import com.example.demo.ServiceKafka.User.UserSavedEvent;
import com.example.demo.ServiceKafka.User.UserUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventConsumer {


    @KafkaListener(topics = "market-deleted", groupId = "market-group")
    public void onMarketDeleted(MarketDeleteEvent event) {
        log.info("DELETED EVENTS: id= {}, name={},location={}, time= {}",event.id(),event.name(),event.location(),event.deletedTime());
    }
    @KafkaListener(topics = "market-updated",groupId = "market-group")
    public void onMarketUpdated(MarketUpdateEvent event){
        log.info("UPDATED MARKET: id= {},name= {}, location= {}, time= {}",event.id(),event.name(),event.location(),event.updatedTime());
    }
    @KafkaListener(topics = "market-saved",groupId = "market-group")
    public void onMarketSaved(MarketSavedEvent event){
        log.info("SAVED EVENT: id= {},name= {}, location= {},time= {}",event.id(),event.name(),event.location(),event.savedTime());
    }
    @KafkaListener(topics = "department-saved",groupId = "department-group")
    public void onDepartmentSaved(DepartmentSavedEvent event){
        log.info("SAVED DEPARTMENT: id= {},name= {},time= {}",event.id(),event.name(),event.savedTime());
    }
    @KafkaListener(topics = "department-deleted",groupId = "department-group")
    public void onDepartmentDeletedEvent(DepartmentDeletedEvent event){
        log.info("DELETED DEPARTMENT: id= {},name= {}, time= {}",event.id(),event.name(),event.deleteTime());
    }
    @KafkaListener(topics = "department-updated",groupId = "department-group")
    public void onDepartmentUpdatedEvent(DepartmentUpdatedEvent event){
        log.info("UPDATED DEPARTMENT: id= {},name= {}, time= {}",event.id(),event.name(),event.updatedTime());
    }
    @KafkaListener(topics = "product-saved",groupId = "product-group")
    public void onProductSaved(ProductSavedEvent event){
        log.info("PRODUCT SAVED: id= {},name= {}, price= {},time= {}",event.id(),event.name(),event.price(),event.savedTime());
    }
    @KafkaListener(topics = "product-delete",groupId = "product-group")
    public void onPublishDeleted(ProductDeletedEvent event){
        log.info("PRODUCT EVENT: id= {},name= {}, price= {}, time= {}",event.id(),event.name(),event.price(),event.deletedTime());
    }
    @KafkaListener(topics = "product-updated",groupId = "product-group")
    public void onPublishUpdate(ProductUpdatedEvent event){
        log.info("PRODUCT UPDATE: id= {},name= {}, price= {}, time= {}",event.id(),event.name(),event.price(),event.updateTime());
    }
    @KafkaListener(topics = "product-deleted",groupId = "user-group")
    public void onPublishSaved(UserSavedEvent event){
        log.info("USER SAVED: id= {}, name= {}, price= {}, time= {}",event.id(),event.username(),event.password(),event.savedTime());

    }
    @KafkaListener(topics = "user-deleted",groupId = "user-group")
    public void onPublishDelete(UserSavedEvent event){
        log.info("USER DELETE: id= {}, name= {}, password= {}, time= {}",event.id(),event.username(),event.password(),event.savedTime());
    }
    @KafkaListener(topics = "user-updated",groupId = "user-group")
    public void onPublishUpdated(UserUpdatedEvent event){
        log.info("USER UPDATE: id= {},name= {}, password= {},time= {}",event.id(),event.username(),event.password(),event.updatedTime());
    }






}

