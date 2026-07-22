package com.rishi.aihub.features.conversation.service;

import com.rishi.aihub.features.conversation.dto.CreateConversationResponse;
import com.rishi.aihub.features.conversation.dto.MessageResponse;
import com.rishi.aihub.features.conversation.entity.Conversation;

import java.util.List;

public interface ConversationService {

    /**
     * Creates a new conversation.
     *
     * @param knowledgeBaseId Knowledge Base Id
     * @return Created conversation response
     */
    CreateConversationResponse createConversation(
            String knowledgeBaseId);

    /**
     * Returns a conversation after validating ownership.
     *
     * @param conversationId Conversation Id
     * @return Conversation
     */
    Conversation getConversation(
            String conversationId);

    /**
     * Saves a user message.
     *
     * @param conversationId Conversation Id
     * @param message User message
     */
    void saveUserMessage(
            String conversationId,
            String message);

    /**
     * Saves an assistant message.
     *
     * @param conversationId Conversation Id
     * @param message Assistant response
     */
    void saveAssistantMessage(
            String conversationId,
            String message);

    /**
     * Returns conversation messages.
     *
     * Implementation uses Redis Cache-Aside pattern.
     *
     * Flow:
     * Redis -> MongoDB -> Redis
     *
     * @param conversationId Conversation Id
     * @return Ordered conversation messages
     */
    List<MessageResponse> getConversationMessages(
            String conversationId);
}