package com.besafx.app.csvparser.infrstructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.largerThan1000Topic}")
    private String largerThan1000Topic;

    @Value("${kafka.topic.lessThan1000Topic}")
    private String lessThan1000Topic;

    @Value("${kafka.topic.errorTopic}")
    private String errorTopic;

    @Bean
    public NewTopic largerThan1000Topic() {
        return new NewTopic(largerThan1000Topic, 1, (short) 1);
    }

    @Bean
    public NewTopic lessThan1000Topic() {
        return new NewTopic(lessThan1000Topic, 1, (short) 1);
    }

    @Bean
    public NewTopic errorTopic() {
        return new NewTopic(errorTopic, 1, (short) 1);
    }

}
