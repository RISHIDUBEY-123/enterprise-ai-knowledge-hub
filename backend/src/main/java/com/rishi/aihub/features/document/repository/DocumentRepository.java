package com.rishi.aihub.features.document.repository;

import com.rishi.aihub.features.document.entity.DocumentMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocumentRepository
        extends MongoRepository<DocumentMetadata, String> {

    List<DocumentMetadata> findByUserId(String userId);

    List<DocumentMetadata> findByUserIdAndKnowledgeBaseId(
            String userId,
            String knowledgeBaseId
    );
}