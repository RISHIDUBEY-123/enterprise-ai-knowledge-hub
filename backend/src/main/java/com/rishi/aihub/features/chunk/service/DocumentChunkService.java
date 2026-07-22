package com.rishi.aihub.features.chunk.service;

import com.rishi.aihub.features.chunk.entity.DocumentChunk;

import java.util.List;

public interface DocumentChunkService {

    /**
     * Save all chunks of a document.
     */
    void saveChunks(List<DocumentChunk> chunks);

    /**
     * Get all chunks for a document.
     */
    List<DocumentChunk> getChunksByDocumentId(String documentId);

    /**
     * Delete all chunks of a document.
     */
    void deleteChunksByDocumentId(String documentId);
}