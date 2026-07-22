package com.rishi.aihub.features.retrieval.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ai.retrieval")
public class RetrievalProperties {

    /**
     * Number of chunks retrieved from Vector DB.
     */
    private int topK = 10;

    /**
     * Minimum similarity threshold.
     */
    private double similarityThreshold = 0.70;

    /**
     * Enable future reranking.
     */
    private boolean rerankingEnabled = false;
}