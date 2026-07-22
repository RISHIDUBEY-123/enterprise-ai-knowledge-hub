package com.rishi.aihub.features.knowledgebase.repository;

import com.rishi.aihub.features.knowledgebase.entity.KnowledgeBase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface KnowledgeBaseRepository extends MongoRepository<KnowledgeBase, String> {

    List<KnowledgeBase> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
}