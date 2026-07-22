package com.rishi.aihub.features.chunk.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "document_chunks")
public class DocumentChunk {

    @Id
    private String id;

    private String documentId;

    private String fileName;

    private Integer chunkIndex;

    private String content;

    private Integer tokenCount;

    private List<Float> embedding;

    private String knowledgeBaseId;

    private String documentName;

    private String userId;
    private Integer pageNumber;
    private String heading;
    private Integer startOffset;
    private Integer endOffset;

}