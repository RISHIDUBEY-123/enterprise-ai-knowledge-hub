package com.rishi.aihub.features.cache.service;

import com.rishi.aihub.features.conversation.dto.MessageResponse;

import java.util.List;
import java.util.Optional;

/**
 * Redis cache service for conversation messages.
 *
 * This service follows the Cache-Aside pattern:
 *
 * 1. Read from Redis
 * 2. If absent -> MongoDB
 * 3. Store in Redis
 * 4. Return result
 */
public interface ConversationMessageCacheService {

    /**
     * Returns cached conversation messages if available.
     *
     * @param conversationId conversation id
     * @return cached messages
     */
    Optional<List<MessageResponse>> get(String conversationId);

    /**
     * Stores conversation messages in Redis.
     *
     * @param conversationId conversation id
     * @param messages conversation messages
     */
    void put(String conversationId, List<MessageResponse> messages);

    /**
     * Removes conversation messages from Redis.
     *
     * @param conversationId conversation id
     */
    void evict(String conversationId);

}