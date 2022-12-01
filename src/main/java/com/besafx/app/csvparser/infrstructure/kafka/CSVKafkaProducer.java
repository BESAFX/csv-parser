package com.besafx.app.csvparser.infrstructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CSVKafkaProducer {

    @Value("${kafka.topic.largerThan1000Topic}")
    private String largerThan1000Topic;

    @Value("${kafka.topic.lessThan1000Topic}")
    private String lessThan1000Topic;

    @Value("${kafka.topic.errorTopic}")
    private String errorTopic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CSVKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendLineWithAmountLargerThan1000(String payload){
        log.info("Sending payload='{}' to topic='{}'", payload, largerThan1000Topic);
        kafkaTemplate.send(largerThan1000Topic, payload);
    }

    public void sendLineWithAmountLessThan1000(String payload){
        log.info("Sending payload='{}' to topic='{}'", payload, lessThan1000Topic);
        kafkaTemplate.send(lessThan1000Topic, payload);
    }

    public void sendNotValidLine(String payload){
        log.info("Sending payload='{}' to topic='{}'", payload, errorTopic);
        kafkaTemplate.send(errorTopic, payload);
    }

}
