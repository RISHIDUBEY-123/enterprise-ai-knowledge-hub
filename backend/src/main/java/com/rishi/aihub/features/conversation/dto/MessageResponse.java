package com.rishi.aihub.features.conversation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageResponse {

    private String role;

    private String content;

}