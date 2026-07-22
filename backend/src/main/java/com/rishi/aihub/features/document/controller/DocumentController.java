package com.rishi.aihub.features.document.controller;

import com.rishi.aihub.common.response.BaseResponse;
import com.rishi.aihub.features.document.dto.DocumentResponse;
import com.rishi.aihub.features.document.dto.UploadDocumentResponse;
import com.rishi.aihub.features.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/knowledge-bases/{knowledgeBaseId}/documents")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UploadDocumentResponse upload(
            @PathVariable String knowledgeBaseId,
            @RequestParam("file") MultipartFile file) {

        return documentService.upload(
                knowledgeBaseId,
                file
        );
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<DocumentResponse>>> getDocuments(
            @PathVariable String knowledgeBaseId) {

        List<DocumentResponse> documents =
                documentService.getDocuments(knowledgeBaseId);

        return ResponseEntity.ok(
                BaseResponse.success(
                        documents,
                        "Documents retrieved successfully."
                )
        );
    }

    @GetMapping("/{documentId}")
    public DocumentResponse getDocument(
            @PathVariable String documentId) {

        return documentService.getDocument(documentId);
    }

    @GetMapping("/{documentId}/download")
    public ResponseEntity<Resource> download(
            @PathVariable String documentId) {

        Resource resource = documentService.download(documentId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\""
                )
                .body(resource);
    }

    @DeleteMapping("/{documentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String documentId) {

        documentService.delete(documentId);
    }

    @PostMapping("/{documentId}/reindex")
    public ResponseEntity<BaseResponse<Void>> reindex(
            @PathVariable String documentId) {

        documentService.reindex(documentId);

        return ResponseEntity.ok(
                BaseResponse.success(
                        null,
                        "Document re-indexing started."
                )
        );
    }
}