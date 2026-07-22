package com.rishi.aihub.features.vector.service;

import com.rishi.aihub.features.chunk.entity.DocumentChunk;
import com.rishi.aihub.features.vector.model.RetrievedChunk;

import java.util.List;

public interface VectorStoreService {

    /**
     * Stores document chunks in the vector database.
     */
    void indexChunks(List<DocumentChunk> chunks);

    /**
     * Performs semantic similarity search within a Knowledge Base.
     */
    List<RetrievedChunk> similaritySearch(
            String knowledgeBaseId,
            String query);

    /**
     * Deletes all vectors belonging to a document.
     * Current implementation is a placeholder until
     * native Qdrant deletion is implemented.
     */
    void deleteDocument(String documentId);
}