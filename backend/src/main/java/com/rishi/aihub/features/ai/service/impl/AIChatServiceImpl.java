package com.rishi.aihub.features.ai.service.impl;

import com.rishi.aihub.features.ai.dto.AIChatResult;
import com.rishi.aihub.features.ai.dto.SourceResponse;
import com.rishi.aihub.features.ai.memory.ConversationMemoryService;
import com.rishi.aihub.features.ai.prompt.PromptBuilder;
import com.rishi.aihub.features.ai.service.AIChatService;
import com.rishi.aihub.features.conversation.entity.Conversation;
import com.rishi.aihub.features.conversation.service.ConversationService;
import com.rishi.aihub.features.retrieval.service.RetrievalService;
import com.rishi.aihub.features.vector.model.RetrievedChunk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AIChatServiceImpl implements AIChatService {

    private final ChatClient chatClient;

    private final ConversationService conversationService;

    private final ConversationMemoryService memoryService;

    private final RetrievalService retrievalService;

    private final PromptBuilder promptBuilder;

    @Override
    public AIChatResult chat(
            String conversationId,
            String question) {

        log.info("Processing AI request for conversation {}", conversationId);

        conversationService.saveUserMessage(
                conversationId,
                question
        );

        List<Message> history =
                memoryService.getConversationMemory(conversationId);

        Conversation conversation =
                conversationService.getConversation(conversationId);

        List<RetrievedChunk> retrievedChunks =
                retrievalService.retrieve(
                        conversation.getKnowledgeBaseId(),
                        question
                );

        String prompt =
                promptBuilder.buildPrompt(
                        history,
                        retrievedChunks,
                        question
                );

        log.debug("Generated Prompt:\n{}", prompt);

        String answer =
                chatClient
                        .prompt()
                        .user(prompt)
                        .call()
                        .content();

        if (answer == null || answer.isBlank()) {
            answer = "Sorry, I couldn't generate a response.";
        }

        conversationService.saveAssistantMessage(
                conversationId,
                answer
        );

        List<SourceResponse> sources =
                retrievedChunks.stream()
                        .map(chunk ->
                                SourceResponse.builder()
                                        .documentName(chunk.getDocumentName())
                                        .pageNumber(chunk.getPageNumber())
                                        .chunkIndex(chunk.getChunkIndex())
                                        .score(chunk.getScore())
                                        .build()
                        )
                        .toList();

        log.info("AI response generated successfully.");

        return AIChatResult.builder()
                .answer(answer)
                .sources(sources)
                .build();
    }

    @Override
    public Flux<String> stream(
            String conversationId,
            String question) {

        log.info("Streaming AI response for conversation {}", conversationId);

        conversationService.saveUserMessage(
                conversationId,
                question
        );

        List<Message> history =
                memoryService.getConversationMemory(conversationId);

        Conversation conversation =
                conversationService.getConversation(conversationId);

        List<RetrievedChunk> retrievedChunks =
                retrievalService.retrieve(
                        conversation.getKnowledgeBaseId(),
                        question
                );

        String prompt =
                promptBuilder.buildPrompt(
                        history,
                        retrievedChunks,
                        question
                );

        StringBuilder finalAnswer = new StringBuilder();

        return chatClient
                .prompt()
                .user(prompt)
                .stream()
                .content()
                .doOnNext(finalAnswer::append)
                .doOnComplete(() ->
                        conversationService.saveAssistantMessage(
                                conversationId,
                                finalAnswer.toString()
                        )
                );
    }
}