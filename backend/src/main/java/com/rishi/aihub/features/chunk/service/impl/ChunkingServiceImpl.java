package com.rishi.aihub.features.chunk.service.impl;

import com.rishi.aihub.features.chunk.entity.DocumentChunk;
import com.rishi.aihub.features.chunk.service.ChunkingService;
import com.rishi.aihub.features.document.entity.DocumentMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChunkingServiceImpl implements ChunkingService {

    private static final int CHUNK_SIZE = 1000;
    private static final int OVERLAP = 200;

    @Override
    public List<DocumentChunk> chunk(
            DocumentMetadata document,
            String content) {

        List<DocumentChunk> chunks = new ArrayList<>();

        if (content == null || content.isBlank()) {
            log.warn("No content found for document {}", document.getId());
            return chunks;
        }

        int chunkIndex = 0;
        int start = 0;

        while (start < content.length()) {

            int end = Math.min(start + CHUNK_SIZE, content.length());

            String chunkContent = content.substring(start, end);

            chunks.add(
                    DocumentChunk.builder()
                            .knowledgeBaseId(document.getKnowledgeBaseId())
                            .userId(document.getUserId())
                            .documentId(document.getId())
                            .documentName(document.getOriginalFileName())

                            .chunkIndex(chunkIndex++)
                            .content(chunkContent)

                            // Metadata for enterprise RAG
                            .pageNumber(1)              // Placeholder until page-aware parsing
                            .startOffset(start)
                            .endOffset(end)
                            .heading(null)

                            // Approximate token count
                            .tokenCount(estimateTokens(chunkContent))

                            .build()
            );

            if (end >= content.length()) {
                break;
            }

            start = end - OVERLAP;
        }

        log.info("Created {} chunks for document {}", chunks.size(), document.getId());

        return chunks;
    }

    /**
     * Approximate token count.
     * Most LLMs average around 4 characters per token for English text.
     */
    private int estimateTokens(String text) {

        if (text == null || text.isBlank()) {
            return 0;
        }

        return Math.max(1, text.length() / 4);
    }
}