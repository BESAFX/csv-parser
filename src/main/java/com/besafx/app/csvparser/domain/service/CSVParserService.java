package com.besafx.app.csvparser.domain.service;

import com.besafx.app.csvparser.domain.exception.ReadFilesException;
import com.besafx.app.csvparser.infrstructure.kafka.CSVKafkaProducer;
import com.google.common.base.Splitter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class CSVParserService {

    private final CSVKafkaProducer csvKafkaProducer;

    @Async("csvParser")
    public Future<Boolean> processSingleFile(MultipartFile file) {

        log.debug("Processing File {}", file.getName());

        final String CSV_REGEX = "^((?:[^,]+,\\s*){2})[^,]+";
        final Pattern pattern = Pattern.compile(CSV_REGEX);
        final Splitter splitter = Splitter.on(Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"));

        try (FileInputStream inputStream = (FileInputStream) file.getInputStream();
             Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8)) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    List<String> columns = splitter.splitToList(line);
                    List<String> temp = new ArrayList<>(columns);
                    long amount = Long.parseLong(columns.get(2));
                    if (amount > 1000) {
                        temp.set(2, String.valueOf(amount * 20));
                        temp.add(String.valueOf((amount * 20) / 100));
                        csvKafkaProducer.sendLineWithAmountLargerThan1000(String.join(", ", temp));
                    } else {
                        temp.add(String.valueOf((amount * 10) / 100));
                        csvKafkaProducer.sendLineWithAmountLessThan1000(String.join(", ", temp));
                    }
                } else csvKafkaProducer.sendNotValidLine(line);
            }
            if (sc.ioException() != null) {
                throw sc.ioException();
            }
            return new AsyncResult<>(true);
        } catch (Exception ex) {
            log.error("processing file failed {}", ex.getMessage());
            return new AsyncResult<>(false);
        }
    }
}
