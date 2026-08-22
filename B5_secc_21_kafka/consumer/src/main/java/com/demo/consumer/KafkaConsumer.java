package com.demo.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    
    /* 
    @KafkaListener(topics = "my-topic", groupId = "my-new-group")
    public void listen(String message){
        System.out.println("Received message: " + message);
    }
    
     
    @KafkaListener(topics = "my-topic", groupId = "my-new-group-1")
    public void listen2(String message) {
        System.out.println("Received message - 1: " + message);
    }
      */  
    
    @KafkaListener(topics = "my-topic", groupId = "my-new-group-rider")
    public void listen3(RiderLocation riderLocation) {
        System.out.println("Received riderLocation: " + riderLocation.getRiderId() + " - " + riderLocation.getLatitude() + " - " + riderLocation.getLongitude() + ".");
    } 
    
}


