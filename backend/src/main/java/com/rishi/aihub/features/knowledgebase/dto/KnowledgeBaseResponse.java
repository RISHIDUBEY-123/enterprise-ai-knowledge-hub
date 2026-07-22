package com.rishi.aihub.features.knowledgebase.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class KnowledgeBaseResponse {

    private String id;

    private String name;

    private String description;

    private Instant createdAt;
}