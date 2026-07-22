package com.rishi.aihub.features.conversation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateConversationRequest {

    @NotBlank
    private String knowledgeBaseId;

}