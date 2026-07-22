package com.rishi.aihub.features.ai.chat.dto;

import com.rishi.aihub.features.ai.dto.SourceResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatMessageResponse {

    private String conversationId;

    private String response;

    /**
     * Source citations used to answer.
     */
    private List<SourceResponse> sources;

}