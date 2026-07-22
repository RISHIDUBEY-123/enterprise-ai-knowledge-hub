package com.rishi.aihub.features.document.service;

import com.rishi.aihub.features.document.dto.DocumentResponse;
import com.rishi.aihub.features.document.dto.UploadDocumentResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {

    UploadDocumentResponse upload(
            String knowledgeBaseId,
            MultipartFile file);

    List<DocumentResponse> getDocuments(
            String knowledgeBaseId);

    DocumentResponse getDocument(
            String documentId);

    Resource download(
            String documentId);

    void delete(
            String documentId);

    void reindex(String documentId);
}