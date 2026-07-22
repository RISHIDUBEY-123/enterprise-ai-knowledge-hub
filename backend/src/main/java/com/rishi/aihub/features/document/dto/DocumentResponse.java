package com.rishi.aihub.features.document.dto;

import com.rishi.aihub.document.entity.DocumentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class DocumentResponse {

    private String id;

    private String fileName;

    private String contentType;

    private Long size;

    private String knowledgeBaseId;

    private DocumentStatus status;

    private Integer pageCount;

    private Integer chunkCount;

    private Integer embeddingCount;

    private Instant indexedAt;
}