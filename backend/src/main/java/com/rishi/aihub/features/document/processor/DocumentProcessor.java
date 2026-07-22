package com.rishi.aihub.features.document.processor;

import com.rishi.aihub.features.document.model.ProcessedDocument;

import java.io.File;

public interface DocumentProcessor {

    boolean supports(String contentType);

    ProcessedDocument process(File file);

}