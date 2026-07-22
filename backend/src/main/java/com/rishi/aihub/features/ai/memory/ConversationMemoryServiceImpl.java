package com.rishi.aihub.features.ai.memory;

import com.rishi.aihub.features.conversation.entity.ChatMessage;
import com.rishi.aihub.features.conversation.entity.MessageRole;
import com.rishi.aihub.features.conversation.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConversationMemoryServiceImpl
        implements ConversationMemoryService {

    private final ChatMessageRepository messageRepository;

    @Override
    public List<Message> getConversationMemory(String conversationId) {

        return messageRepository
                .findByConversationIdOrderByTimestampAsc(conversationId)
                .stream()
                .<Message>map(message -> {

                    if (message.getRole() == MessageRole.USER) {
                        return new UserMessage(message.getContent());
                    }

                    return new AssistantMessage(message.getContent());
                })
                .toList();
    }
}