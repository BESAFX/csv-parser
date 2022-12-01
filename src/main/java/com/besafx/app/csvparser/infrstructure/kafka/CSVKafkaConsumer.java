package com.besafx.app.csvparser.infrstructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Service
@Slf4j
public class CSVKafkaConsumer {

    @Value("${kafka.result.largerThan1000FilePath}")
    private String largerThan1000FilePath;

    @Value("${kafka.result.lessThan1000FilePath}")
    private String lessThan1000FilePath;

    @Value("${kafka.result.errorFilePath}")
    private String errorFilePath;

    @KafkaListener(topics = "kafkaLargerThan1000Topic", groupId = "csv-1")
    @Async("lineAppender")
    public void storeLineWithAmountLargerThan1000(String payload) {
        appendLineToFile(payload, largerThan1000FilePath);
    }

    @KafkaListener(topics = "kafkaLessThan1000Topic", groupId = "csv-1")
    @Async("lineAppender")
    public void storeLineWithAmountLessThan1000(String payload) {
        appendLineToFile(payload, lessThan1000FilePath);
    }

    @KafkaListener(topics = "kafkaErrorTopic", groupId = "csv-1")
    @Async("lineAppender")
    public void storeNotValidLine(String payload) {
        appendLineToFile(payload, errorFilePath);
    }

    public void appendLineToFile(String line, String fileName) {
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            log.debug("appending line [{}] to file [{}]", line, fileName);
            out.println(line);
        } catch (IOException e) {
            log.error("appending line failed [{}]", e.getMessage());
        }
    }

}
