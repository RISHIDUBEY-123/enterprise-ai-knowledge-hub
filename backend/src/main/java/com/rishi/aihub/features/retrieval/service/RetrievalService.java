package com.rishi.aihub.features.retrieval.service;

import com.rishi.aihub.features.vector.model.RetrievedChunk;

import java.util.List;

public interface RetrievalService {

    /**
     * Retrieves the most relevant chunks for a user's question.
     */
    List<RetrievedChunk> retrieve(
            String knowledgeBaseId,
            String question);

}