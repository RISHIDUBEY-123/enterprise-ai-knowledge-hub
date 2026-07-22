package com.rishi.aihub.features.chunk.repository;

import com.rishi.aihub.features.chunk.entity.DocumentChunk;

import java.util.List;

public interface DocumentChunkRepositoryCustom {

    List<DocumentChunk> keywordSearch(
            String knowledgeBaseId,
            String query,
            int limit
    );

}
