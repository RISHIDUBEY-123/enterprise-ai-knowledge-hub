package com.rishi.aihub.features.ai.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessedDocument {

    private String fileName;

    private String content;

    private int pageCount;

    private String contentType;

}