package com.rishi.aihub.features.document.entity;

import com.rishi.aihub.common.audit.AuditEntity;
import com.rishi.aihub.document.entity.DocumentStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "documents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentMetadata extends AuditEntity {

    @Id
    private String id;

    private String userId;

    private String knowledgeBaseId;

    private String originalFileName;

    private String storedFileName;

    private String contentType;

    private Long size;

    private String storagePath;

    private DocumentStatus status;

    private Integer pageCount;

    private Integer chunkCount;

    private Integer embeddingCount;

    private Instant indexedAt;

    private String failureReason;
}
