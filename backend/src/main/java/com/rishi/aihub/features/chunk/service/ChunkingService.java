package com.rishi.aihub.features.chunk.service;

import com.rishi.aihub.features.chunk.entity.DocumentChunk;
import com.rishi.aihub.features.chunk.model.Chunk;
import com.rishi.aihub.features.document.entity.DocumentMetadata;

import java.util.List;

public interface ChunkingService {

    List<DocumentChunk> chunk(
            DocumentMetadata document,
            String content
    );

}