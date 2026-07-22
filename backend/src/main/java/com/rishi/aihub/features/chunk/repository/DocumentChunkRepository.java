package com.rishi.aihub.features.chunk.repository;

import com.rishi.aihub.features.chunk.entity.DocumentChunk;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocumentChunkRepository extends MongoRepository<DocumentChunk, String>,
        DocumentChunkRepositoryCustom {

    List<DocumentChunk> findByDocumentId(String documentId);

    void deleteByDocumentId(String documentId);
}