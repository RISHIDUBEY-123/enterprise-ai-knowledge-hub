package com.rishi.aihub.features.document.processing;

import com.rishi.aihub.features.document.factory.DocumentProcessorFactory;
import com.rishi.aihub.features.document.model.ProcessedDocument;
import com.rishi.aihub.features.document.processing.service.DocumentProcessingService;
import com.rishi.aihub.features.document.processor.DocumentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class DocumentProcessingServiceImpl
        implements DocumentProcessingService {

    private final DocumentProcessorFactory processorFactory;

    @Override
    public ProcessedDocument process(
            File file,
            String contentType) {

        DocumentProcessor processor =
                processorFactory.getProcessor(contentType);

        return processor.process(file);

    }

}