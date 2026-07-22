package com.rishi.aihub.features.conversation.repository;

import com.rishi.aihub.features.conversation.entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ConversationRepository
        extends MongoRepository<Conversation, String> {

    List<Conversation> findByUserIdOrderByUpdatedAtDesc(String userId);
}