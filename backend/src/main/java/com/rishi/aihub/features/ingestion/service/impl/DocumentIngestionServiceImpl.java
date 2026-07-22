package com.rishi.aihub.features.ingestion.service.impl;

import com.rishi.aihub.common.exception.ResourceNotFoundException;
import com.rishi.aihub.document.entity.DocumentStatus;
import com.rishi.aihub.features.chunk.entity.DocumentChunk;
import com.rishi.aihub.features.chunk.service.ChunkingService;
import com.rishi.aihub.features.chunk.service.DocumentChunkService;
import com.rishi.aihub.features.document.entity.DocumentMetadata;
import com.rishi.aihub.features.document.model.ProcessedDocument;
import com.rishi.aihub.features.document.processing.service.DocumentProcessingService;
import com.rishi.aihub.features.document.repository.DocumentRepository;
import com.rishi.aihub.features.document.storage.StorageService;
import com.rishi.aihub.features.ingestion.service.DocumentIngestionService;
import com.rishi.aihub.features.vector.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentIngestionServiceImpl implements DocumentIngestionService {

    private final DocumentRepository documentRepository;
    private final DocumentProcessingService processingService;
    private final DocumentChunkService chunkService;
    private final VectorStoreService vectorStoreService;
    private final StorageService storageService;
    private final ChunkingService chunkingService;

    @Override
    public void ingest(String documentId) {

        DocumentMetadata metadata = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document", documentId));

        try {

            log.info("Starting ingestion for document: {}", documentId);

            File file = storageService.loadAsFile(metadata.getStoredFileName());

            ProcessedDocument processed =
                    processingService.process(file, metadata.getContentType());

            List<DocumentChunk> chunks =
                    chunkingService.chunk(metadata, processed.getContent());

            chunkService.saveChunks(chunks);

            vectorStoreService.indexChunks(chunks);

            markAsIndexed(metadata, processed, chunks);

            log.info("Document {} indexed successfully.", documentId);

        } catch (Exception ex) {

            log.error("Failed to ingest document {}", documentId, ex);

            markAsFailed(metadata, ex);

            throw ex;
        }
    }

    /**
     * Marks the document as successfully indexed.
     */
    private void markAsIndexed(
            DocumentMetadata document,
            ProcessedDocument processed,
            List<DocumentChunk> chunks) {

        document.setStatus(DocumentStatus.INDEXED);
        document.setIndexedAt(Instant.now());
        document.setPageCount(processed.getPageCount());
        document.setChunkCount(chunks.size());

        // One embedding per chunk
        document.setEmbeddingCount(chunks.size());

        document.setFailureReason(null);

        documentRepository.save(document);
    }

    /**
     * Marks the document as failed.
     */
    private void markAsFailed(
            DocumentMetadata document,
            Exception exception) {

        document.setStatus(DocumentStatus.FAILED);
        document.setFailureReason(exception.getMessage());

        documentRepository.save(document);
    }

}