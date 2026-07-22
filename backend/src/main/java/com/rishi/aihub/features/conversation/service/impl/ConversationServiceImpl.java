package com.rishi.aihub.features.conversation.service.impl;

import com.rishi.aihub.common.exception.ForbiddenException;
import com.rishi.aihub.common.exception.ResourceNotFoundException;
import com.rishi.aihub.features.auth.security.CurrentUserService;
import com.rishi.aihub.features.cache.service.ConversationMessageCacheService;
import com.rishi.aihub.features.conversation.dto.CreateConversationResponse;
import com.rishi.aihub.features.conversation.dto.MessageResponse;
import com.rishi.aihub.features.conversation.entity.ChatMessage;
import com.rishi.aihub.features.conversation.entity.Conversation;
import com.rishi.aihub.features.conversation.entity.MessageRole;
import com.rishi.aihub.features.conversation.repository.ChatMessageRepository;
import com.rishi.aihub.features.conversation.repository.ConversationRepository;
import com.rishi.aihub.features.conversation.service.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final ChatMessageRepository messageRepository;
    private final CurrentUserService currentUserService;
    private final ConversationMessageCacheService conversationMessageCacheService;

    @Override
    @Transactional
    public CreateConversationResponse createConversation(String knowledgeBaseId) {

        String userId = currentUserService.getCurrentUserId();

        Conversation conversation = Conversation.builder()
                .userId(userId)
                .knowledgeBaseId(knowledgeBaseId)
                .title("New Conversation")
                .build();

        Conversation savedConversation =
                conversationRepository.save(conversation);

        log.info("Conversation created with id={}",
                savedConversation.getId());

        return CreateConversationResponse.builder()
                .conversationId(savedConversation.getId())
                .build();
    }

    @Override
    public Conversation getConversation(String conversationId) {

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Conversation",
                                conversationId));

        validateOwnership(conversation);

        return conversation;
    }

    @Override
    @Transactional
    public void saveUserMessage(String conversationId,
                                String message) {

        ChatMessage chatMessage = ChatMessage.builder()
                .conversationId(conversationId)
                .role(MessageRole.USER)
                .content(message)
                .timestamp(Instant.now())
                .build();

        messageRepository.save(chatMessage);

        updateConversationTimestamp(conversationId);

        conversationMessageCacheService.evict(conversationId);

        log.debug("User message saved for conversation={}",
                conversationId);
    }

    @Override
    @Transactional
    public void saveAssistantMessage(String conversationId,
                                     String message) {

        ChatMessage chatMessage = ChatMessage.builder()
                .conversationId(conversationId)
                .role(MessageRole.ASSISTANT)
                .content(message)
                .timestamp(Instant.now())
                .build();

        messageRepository.save(chatMessage);

        updateConversationTimestamp(conversationId);

        conversationMessageCacheService.evict(conversationId);

        log.debug("Assistant message saved for conversation={}",
                conversationId);
    }
    /**
     * Validates that the logged-in user owns the conversation.
     */
    private void validateOwnership(Conversation conversation) {

        String currentUserId = currentUserService.getCurrentUserId();

        if (!conversation.getUserId().equals(currentUserId)) {
            throw new ForbiddenException(
                    "You are not authorized to access this conversation."
            );
        }
    }
    /**
     * Updates the conversation's last modified timestamp.
     */
    private void updateConversationTimestamp(String conversationId) {

        conversationRepository.findById(conversationId)
                .ifPresent(conversation -> {
                    conversation.setUpdatedAt(Instant.now());
                    conversationRepository.save(conversation);
                });
    }
    @Override
    public List<MessageResponse> getConversationMessages(String conversationId) {

        // Validate ownership
        getConversation(conversationId);

        // Check Redis first
        Optional<List<MessageResponse>> cachedMessages =
                conversationMessageCacheService.get(conversationId);

        if (cachedMessages.isPresent()) {

            log.debug("Conversation messages loaded from Redis for conversation={}",
                    conversationId);

            return cachedMessages.get();
        }

        log.debug("Redis cache miss for conversation={}. Loading from MongoDB.",
                conversationId);

        List<MessageResponse> messages = messageRepository
                .findByConversationIdOrderByTimestampAsc(conversationId)
                .stream()
                .map(message -> MessageResponse.builder()
                        .role(message.getRole().name())
                        .content(message.getContent())
                        .build())
                .toList();

        // Cache the messages
        conversationMessageCacheService.put(conversationId, messages);

        return messages;
    }
}