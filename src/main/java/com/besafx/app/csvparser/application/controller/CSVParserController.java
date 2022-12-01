package com.besafx.app.csvparser.application.controller;

import com.besafx.app.csvparser.domain.dto.CSVResult;
import com.besafx.app.csvparser.domain.service.CSVParserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/api/parser/csv/")
@AllArgsConstructor
@Slf4j
public class CSVParserController {

    private final CSVParserService csvParserService;

    @PostMapping(value = "read", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CSVResult> readFiles(@RequestBody MultipartFile[] files) throws Exception {
        List<String> passedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            Boolean result = csvParserService.processSingleFile(file).get();
            if (result) passedFiles.add(file.getOriginalFilename());
            else failedFiles.add(file.getOriginalFilename());
        }
        return ResponseEntity.ok(CSVResult
                .builder()
                .passedFiles(passedFiles)
                .failedFiles(failedFiles)
                .build()
        );
    }

}
