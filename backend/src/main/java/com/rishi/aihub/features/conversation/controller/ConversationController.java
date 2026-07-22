package com.rishi.aihub.features.conversation.controller;

import com.rishi.aihub.common.response.BaseResponse;
import com.rishi.aihub.features.ai.chat.dto.ChatMessageResponse;
import com.rishi.aihub.features.ai.chat.dto.ChatRequest;
import com.rishi.aihub.features.ai.orchestrator.AIOrchestrator;
import com.rishi.aihub.features.conversation.dto.CreateConversationResponse;
import com.rishi.aihub.features.conversation.service.ConversationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversations")
public class ConversationController {

    private final AIOrchestrator aiOrchestrator;
    private final ConversationService conversationService;

    @PostMapping("/{conversationId}/chat")
    public ResponseEntity<BaseResponse<ChatMessageResponse>> chat(
            @PathVariable String conversationId,
            @Valid @RequestBody ChatRequest request) {

        ChatMessageResponse response =
                aiOrchestrator.chat(
                        conversationId,
                        request.getMessage()
                );

        return ResponseEntity.ok(
                BaseResponse.success(
                        response,
                        "Response generated successfully."
                )
        );
    }

    @PostMapping(
            value = "/{conversationId}/stream",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public Flux<String> stream(
            @PathVariable String conversationId,
            @Valid @RequestBody ChatRequest request) {

        return aiOrchestrator.stream(
                conversationId,
                request.getMessage()
        );
    }

    @PostMapping
    public ResponseEntity<BaseResponse<CreateConversationResponse>> createConversation(
            @RequestParam String knowledgeBaseId) {

        CreateConversationResponse response =
                conversationService.createConversation(knowledgeBaseId);

        return ResponseEntity.ok(
                BaseResponse.success(
                        response,
                        "Conversation created successfully."
                )
        );
    }

}