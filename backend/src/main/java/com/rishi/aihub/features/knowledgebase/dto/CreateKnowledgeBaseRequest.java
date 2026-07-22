package com.rishi.aihub.features.knowledgebase.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateKnowledgeBaseRequest {

    @NotBlank(message = "Knowledge base name is required")
    private String name;

    private String description;
}