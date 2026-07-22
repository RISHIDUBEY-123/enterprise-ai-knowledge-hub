package com.rishi.aihub.features.ai.orchestrator;

import com.rishi.aihub.features.ai.chat.dto.ChatMessageResponse;
import com.rishi.aihub.features.ai.dto.AIChatResult;
import com.rishi.aihub.features.ai.service.AIChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIOrchestratorImpl implements AIOrchestrator {

    private final AIChatService aiChatService;

    @Override
    public ChatMessageResponse chat(
            String conversationId,
            String message) {

        log.info("Processing AI request for conversation: {}", conversationId);

        AIChatResult result = aiChatService.chat(
                conversationId,
                message
        );

        log.info("AI response generated successfully for conversation: {}", conversationId);

        return ChatMessageResponse.builder()
                .conversationId(conversationId)
                .response(result.getAnswer())
                .sources(result.getSources())
                .build();
    }

    @Override
    public Flux<String> stream(
            String conversationId,
            String message) {

        log.info("Streaming AI request for conversation: {}", conversationId);

        return aiChatService.stream(
                conversationId,
                message
        );
    }
}