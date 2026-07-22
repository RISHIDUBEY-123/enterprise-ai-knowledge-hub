package com.rishi.aihub.features.document.processing.service;

import com.rishi.aihub.features.document.model.ProcessedDocument;

import java.io.File;

public interface DocumentProcessingService {

    ProcessedDocument process(
            File file,
            String contentType
    );

}