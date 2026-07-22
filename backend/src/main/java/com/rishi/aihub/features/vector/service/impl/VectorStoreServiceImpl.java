package com.rishi.aihub.features.vector.service.impl;

import com.rishi.aihub.features.chunk.entity.DocumentChunk;
import com.rishi.aihub.features.retrieval.config.RetrievalProperties;
import com.rishi.aihub.features.vector.model.RetrievedChunk;
import com.rishi.aihub.features.vector.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class VectorStoreServiceImpl implements VectorStoreService {

    private final RetrievalProperties retrievalProperties;

    private static final String KNOWLEDGE_BASE_ID = "knowledgeBaseId";
    private static final String DOCUMENT_ID = "documentId";
    private static final String DOCUMENT_NAME = "documentName";
    private static final String CHUNK_INDEX = "chunkIndex";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String USER_ID = "userId";

    private final VectorStore vectorStore;

    @Override
    public void indexChunks(List<DocumentChunk> chunks) {

        if (chunks == null || chunks.isEmpty()) {
            log.warn("No chunks to index.");
            return;
        }

        List<Document> documents = chunks.stream()
                .map(this::toDocument)
                .toList();

        vectorStore.add(documents);

        log.info("Successfully indexed {} chunks into Qdrant.", documents.size());
    }

    @Override
    public List<RetrievedChunk> similaritySearch(
            String knowledgeBaseId,
            String query) {

        log.debug("Performing semantic search for knowledgeBaseId={}", knowledgeBaseId);

        SearchRequest request = buildSearchRequest(
                knowledgeBaseId,
                query
        );

        List<Document> results = vectorStore.similaritySearch(request);

        if (results == null || results.isEmpty()) {
            log.info("No matching chunks found.");
            return List.of();
        }

        log.info("Retrieved {} relevant chunks.", results.size());

        return results.stream()
                .map(this::toRetrievedChunk)
                .distinct()
                .filter(chunk ->
                        chunk.getScore() == null ||
                                chunk.getScore() >= retrievalProperties.getSimilarityThreshold())
                .toList();
    }

    private SearchRequest buildSearchRequest(
            String knowledgeBaseId,
            String query) {

        return SearchRequest.builder()
                .query(query)
                .topK(retrievalProperties.getTopK())
                .filterExpression(
                        KNOWLEDGE_BASE_ID + " == '" + knowledgeBaseId + "'"
                )
                .build();
    }

    private RetrievedChunk toRetrievedChunk(Document document) {

        Map<String, Object> metadata = document.getMetadata();

        return RetrievedChunk.builder()
                .documentId(getString(metadata, DOCUMENT_ID))
                .documentName(getString(metadata, DOCUMENT_NAME))
                .pageNumber(toInteger(metadata.get(PAGE_NUMBER), 1))
                .chunkIndex(toInteger(metadata.get(CHUNK_INDEX), 0))
                .content(document.getText())
                .score(document.getScore())
                .build();
    }

    private Document toDocument(DocumentChunk chunk) {

        Map<String, Object> metadata = new HashMap<>();

        metadata.put(KNOWLEDGE_BASE_ID, chunk.getKnowledgeBaseId());
        metadata.put(DOCUMENT_ID, chunk.getDocumentId());
        metadata.put(DOCUMENT_NAME, chunk.getDocumentName());
        metadata.put(CHUNK_INDEX, chunk.getChunkIndex());
        metadata.put(PAGE_NUMBER, chunk.getPageNumber());
        metadata.put(USER_ID, chunk.getUserId());

        return new Document(
                chunk.getContent(),
                metadata
        );
    }

    private Integer toInteger(Object value, Integer defaultValue) {

        if (value == null) {
            return defaultValue;
        }

        if (value instanceof Integer integer) {
            return integer;
        }

        if (value instanceof Number number) {
            return number.intValue();
        }

        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    private String getString(Map<String, Object> metadata, String key) {

        Object value = metadata.get(key);

        return value == null ? null : value.toString();
    }

    @Override
    public void deleteDocument(String documentId) {

        log.warn("Vector deletion is not implemented yet for documentId={}", documentId);

        /*
         * TODO
         *
         * Replace this implementation with the native Qdrant client.
         *
         * Delete all vector points whose metadata contains:
         *
         * documentId = <documentId>
         */
    }
}