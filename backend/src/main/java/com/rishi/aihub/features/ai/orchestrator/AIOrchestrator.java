package com.rishi.aihub.features.ai.orchestrator;

import com.rishi.aihub.features.ai.chat.dto.ChatMessageResponse;
import reactor.core.publisher.Flux;

public interface AIOrchestrator {

    ChatMessageResponse chat(
            String conversationId,
            String message
    );

    Flux<String> stream(
            String conversationId,
            String message
    );
}