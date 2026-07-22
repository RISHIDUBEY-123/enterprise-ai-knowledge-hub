package com.rishi.aihub.features.document.service.impl;

import com.rishi.aihub.common.exception.ForbiddenException;
import com.rishi.aihub.common.exception.ResourceNotFoundException;
import com.rishi.aihub.document.entity.DocumentStatus;
import com.rishi.aihub.features.auth.security.CurrentUserService;
import com.rishi.aihub.features.chunk.service.DocumentChunkService;
import com.rishi.aihub.features.document.dto.DocumentResponse;
import com.rishi.aihub.features.document.dto.UploadDocumentResponse;
import com.rishi.aihub.features.document.entity.DocumentMetadata;
import com.rishi.aihub.features.document.mapper.DocumentMapper;
import com.rishi.aihub.features.document.repository.DocumentRepository;
import com.rishi.aihub.features.document.service.DocumentService;
import com.rishi.aihub.features.document.storage.StorageService;
import com.rishi.aihub.features.document.validator.FileValidator;
import com.rishi.aihub.features.ingestion.service.DocumentIngestionService;
import com.rishi.aihub.features.knowledgebase.entity.KnowledgeBase;
import com.rishi.aihub.features.knowledgebase.repository.KnowledgeBaseRepository;
import com.rishi.aihub.features.vector.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final StorageService storageService;
    private final FileValidator fileValidator;
    private final CurrentUserService currentUserService;
    private final DocumentMapper documentMapper;
    private final DocumentIngestionService ingestionService;
    private final KnowledgeBaseRepository knowledgeBaseRepository;
    private final DocumentChunkService documentChunkService;
    private final VectorStoreService vectorStoreService;

    @Override
    public UploadDocumentResponse upload(
            String knowledgeBaseId,
            MultipartFile file) {

        log.info("Uploading document: {}", file.getOriginalFilename());

        // Validate Knowledge Base
        KnowledgeBase knowledgeBase = knowledgeBaseRepository.findById(knowledgeBaseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("KnowledgeBase", knowledgeBaseId));

        if (!knowledgeBase.getOwnerId().equals(currentUserService.getCurrentUserId())) {
            throw new ForbiddenException(
                    "You are not authorized to upload documents to this Knowledge Base."
            );
        }

        // Validate File
        fileValidator.validate(file);

        // Store File
        String storedFileName = storageService.store(file);

        // Save Metadata
        DocumentMetadata document = DocumentMetadata.builder()
                .userId(currentUserService.getCurrentUserId())
                .knowledgeBaseId(knowledgeBaseId)
                .originalFileName(file.getOriginalFilename())
                .storedFileName(storedFileName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .storagePath(storedFileName)
                .status(DocumentStatus.PROCESSING)
                .pageCount(0)
                .chunkCount(0)
                .embeddingCount(0)
                .failureReason(null)
                .indexedAt(null)
                .build();

        DocumentMetadata savedDocument = documentRepository.save(document);

        // Trigger ingestion
        ingestionService.ingest(savedDocument.getId());

        log.info("Document uploaded successfully with id: {}", savedDocument.getId());

        return UploadDocumentResponse.builder()
                .documentId(savedDocument.getId())
                .fileName(savedDocument.getOriginalFileName())
                .build();
    }

    @Override
    public List<DocumentResponse> getDocuments(
            String knowledgeBaseId) {

        String userId = currentUserService.getCurrentUserId();

        // Validate Knowledge Base ownership
        KnowledgeBase knowledgeBase = knowledgeBaseRepository.findById(knowledgeBaseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("KnowledgeBase", knowledgeBaseId));

        if (!knowledgeBase.getOwnerId().equals(userId)) {
            throw new ForbiddenException(
                    "You are not authorized to access this Knowledge Base."
            );
        }

        log.info("Fetching documents for Knowledge Base: {}", knowledgeBaseId);

        return documentRepository
                .findByUserIdAndKnowledgeBaseId(userId, knowledgeBaseId)
                .stream()
                .map(documentMapper::toResponse)
                .toList();
    }

    @Override
    public DocumentResponse getDocument(
            String documentId) {

        DocumentMetadata document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document", documentId));

        validateOwnership(document);

        return documentMapper.toResponse(document);
    }

    @Override
    public Resource download(
            String documentId) {

        DocumentMetadata document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document", documentId));

        validateOwnership(document);

        log.info("Downloading document {}", documentId);

        return storageService.loadAsResource(document.getStoredFileName());
    }

    @Override
    public void delete(String documentId) {

        DocumentMetadata document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document", documentId));

        validateOwnership(document);

        storageService.delete(document.getStoragePath());

        documentChunkService.deleteChunksByDocumentId(documentId);

        // Placeholder until native Qdrant deletion is implemented
        vectorStoreService.deleteDocument(documentId);

        documentRepository.delete(document);

        log.info("Document deleted successfully: {}", documentId);
    }

    /**
     * Ensures the logged-in user owns the document.
     */
    private void validateOwnership(DocumentMetadata document) {

        String currentUserId = currentUserService.getCurrentUserId();

        if (!document.getUserId().equals(currentUserId)) {
            throw new ForbiddenException(
                    "You are not authorized to access this document."
            );
        }
    }

    @Override
    public void reindex(String documentId) {

        DocumentMetadata document = documentRepository.findById(documentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Document", documentId));

        validateOwnership(document);

        log.info("Re-indexing document {}", documentId);

        // Delete old chunks
        documentChunkService.deleteChunksByDocumentId(documentId);

        // Delete vectors
        vectorStoreService.deleteDocument(documentId);

        // Reset document metadata
        document.setStatus(DocumentStatus.PROCESSING);
        document.setChunkCount(0);
        document.setEmbeddingCount(0);
        document.setPageCount(0);
        document.setIndexedAt(null);
        document.setFailureReason(null);

        documentRepository.save(document);

        // Start ingestion again
        ingestionService.ingest(documentId);
    }

}