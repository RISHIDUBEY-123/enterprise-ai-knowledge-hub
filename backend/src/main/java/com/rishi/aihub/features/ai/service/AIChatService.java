package com.rishi.aihub.features.ai.service;

import com.rishi.aihub.features.ai.dto.AIChatResult;
import reactor.core.publisher.Flux;

public interface AIChatService {

    AIChatResult chat(
            String conversationId,
            String question
    );

    Flux<String> stream(
            String conversationId,
            String question
    );
}