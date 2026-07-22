package com.rishi.aihub.features.chunk.service.impl;

import com.rishi.aihub.features.chunk.entity.DocumentChunk;
import com.rishi.aihub.features.chunk.repository.DocumentChunkRepository;
import com.rishi.aihub.features.chunk.service.DocumentChunkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentChunkServiceImpl implements DocumentChunkService {

    private final DocumentChunkRepository documentChunkRepository;

    @Override
    public void saveChunks(List<DocumentChunk> chunks) {

        if (chunks == null || chunks.isEmpty()) {
            return;
        }

        documentChunkRepository.saveAll(chunks);
    }

    @Override
    public List<DocumentChunk> getChunksByDocumentId(String documentId) {
        return documentChunkRepository.findByDocumentId(documentId);
    }

    @Override
    public void deleteChunksByDocumentId(String documentId) {
        documentChunkRepository.deleteByDocumentId(documentId);
    }
}