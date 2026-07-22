package com.rishi.aihub.features.knowledgebase.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "knowledge_bases")
public class KnowledgeBase {

    @Id
    private String id;

    private String ownerId;

    private String name;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;
}