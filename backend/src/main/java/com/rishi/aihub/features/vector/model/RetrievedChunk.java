package com.rishi.aihub.features.vector.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RetrievedChunk {

    /**
     * Chunk ID.
     */
    private String chunkId;

    /**
     * Parent document.
     */
    private String documentId;

    /**
     * Original file name.
     */
    private String documentName;

    /**
     * Chunk content.
     */
    private String content;

    /**
     * Page number.
     */
    private Integer pageNumber;

    /**
     * Chunk order.
     */
    private Integer chunkIndex;

    /**
     * Similarity score returned by vector search.
     */
    private Double score;

}