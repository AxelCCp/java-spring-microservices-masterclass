package com.demo.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class KafkaProducer {

    private final KafkaTemplate <String, String> kafkaTemplate;

    private final KafkaTemplate <String, RiderLocation> kafkaTemplate2;

    public KafkaProducer(KafkaTemplate <String, String> kafkaTemplate, KafkaTemplate <String, RiderLocation> kafkaTemplate2) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate2 = kafkaTemplate2;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        kafkaTemplate.send("my-topic", message);
        return "message - sent: " + message;
    }

    @PostMapping("/send-obj")
    public String sendMessage2(@RequestParam String message) {
        RiderLocation location = new RiderLocation("ride123", 28.61, 77.23);
        kafkaTemplate2.send("my-topic", location);
        return "message - sent: " + location.getRiderId();
    }

}
