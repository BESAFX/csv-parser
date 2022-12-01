package com.besafx.app.csvparser.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class CSVResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> passedFiles = new ArrayList<>();
    private List<String> failedFiles = new ArrayList<>();
}
