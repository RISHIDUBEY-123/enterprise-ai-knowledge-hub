package com.rishi.aihub.document.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "documents")
public class DocumentEntity {

    @Id
    private String id;

    private String knowledgeBaseId;

    private String userId;

    private String fileName;

    private String originalFileName;

    private String contentType;

    private Long fileSize;

    private Integer pageCount;

    private Integer chunkCount;

    private Integer embeddingCount;

    private DocumentStatus status;

    private String storagePath;

    private Instant uploadedAt;

    private Instant indexedAt;

    private Instant updatedAt;

}