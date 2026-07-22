package com.rishi.aihub.features.embedding.service;

import java.util.List;

public interface EmbeddingService {

    /**
     * Generates an embedding vector for the supplied text.
     */
    List<Float> generateEmbedding(String text);

}