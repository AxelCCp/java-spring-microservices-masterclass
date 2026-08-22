package com.demo.producer;

import java.util.Random;
import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class KafkaProducerStream {

    //private final KafkaTemplate <String, String> kafkaTemplate;
    //private final KafkaTemplate <String, RiderLocation> kafkaTemplate2;
    
    @Bean 
    public Supplier <RiderLocation> sendRiderLocation() {
        Random random = new Random();
        return () -> {
            String riderId = "rider" + random.nextInt(20); 
            //RiderLocation location = new RiderLocation("rider-7", 16.7, 15.7);  //sin particion
            RiderLocation location = new RiderLocation(riderId, 16.7, 15.7); //con particion
            System.out.println("Sending: " + location.getRiderId());
            return location;
        };
    }

    //mas de un topico en una app producer:
    @Bean 
    public Supplier <Message<String>> sendRiderStatus() {
        Random random = new Random();
        return () -> {

            String riderId = "rider" + random.nextInt(20); 
            String status = random.nextBoolean() ? "ride started" : "ride completed";
            
            System.out.println("Sending: " + status);
            
            return MessageBuilder.withPayload(riderId + ":" + status)
            .setHeader(KafkaHeaders.KEY, riderId.getBytes())
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
            .build();
        };
    }

  

}
