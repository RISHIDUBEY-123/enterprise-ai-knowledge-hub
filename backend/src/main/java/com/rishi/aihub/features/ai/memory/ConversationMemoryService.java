package com.rishi.aihub.features.ai.memory;

import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface ConversationMemoryService {

    List<Message> getConversationMemory(String conversationId);


}