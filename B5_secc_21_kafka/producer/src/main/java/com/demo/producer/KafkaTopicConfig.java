package com.demo.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic creaNewTopic() {
        return new NewTopic("topic-aaa", 3, (short)1);
    }

}
