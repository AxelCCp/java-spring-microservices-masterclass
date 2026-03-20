package com.demo.producer;

import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerStream {

    //private final KafkaTemplate <String, String> kafkaTemplate;
    //private final KafkaTemplate <String, RiderLocation> kafkaTemplate2;
    
    @Bean 
    public Supplier <RiderLocation> sendRiderLocation() {
        return () -> {
            RiderLocation location = new RiderLocation("rider-7", 16.7, 15.7);
            System.out.println("Sending: " + location.getRiderId());
            return location;
        };
    }

  

}
