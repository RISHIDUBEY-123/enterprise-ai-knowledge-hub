package com.rishi.aihub.features.document.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ProcessedDocument {

    private String title;

    private String content;

    private Integer pageCount;

    private Integer characterCount;

    private Integer wordCount;

    private String language;

    private Map<String, Object> metadata;
}