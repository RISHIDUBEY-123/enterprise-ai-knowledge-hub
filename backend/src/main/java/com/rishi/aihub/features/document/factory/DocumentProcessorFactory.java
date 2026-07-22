package com.rishi.aihub.features.document.factory;

import com.rishi.aihub.common.exception.BusinessException;
import com.rishi.aihub.features.document.processor.DocumentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DocumentProcessorFactory {

    private final List<DocumentProcessor> processors;

    public DocumentProcessor getProcessor(String contentType){

        return processors.stream()
                .filter(p -> p.supports(contentType))
                .findFirst()
                .orElseThrow(() ->
                        new BusinessException(
                                "Unsupported document type : "
                                        + contentType
                        ));

    }

}