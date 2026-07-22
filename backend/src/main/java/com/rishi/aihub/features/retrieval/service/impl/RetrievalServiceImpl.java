package com.rishi.aihub.features.retrieval.service.impl;

import com.rishi.aihub.features.retrieval.service.RetrievalService;
import com.rishi.aihub.features.vector.model.RetrievedChunk;
import com.rishi.aihub.features.vector.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RetrievalServiceImpl implements RetrievalService {

    private final VectorStoreService vectorStoreService;

    @Override
    public List<RetrievedChunk> retrieve(
            String knowledgeBaseId,
            String question) {

        log.info(
                "Retrieving chunks for knowledgeBaseId={} question={}",
                knowledgeBaseId,
                question
        );

        List<RetrievedChunk> chunks =
                vectorStoreService.similaritySearch(
                        knowledgeBaseId,
                        question
                );

        log.info("Retrieved {} chunks.", chunks.size());

        return chunks;
    }
}