package com.rishi.aihub.features.document.processor;

import com.rishi.aihub.common.exception.BusinessException;
import com.rishi.aihub.features.document.model.ProcessedDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

@Component
@Slf4j
public class TextProcessor implements DocumentProcessor {

    @Override
    public boolean supports(String contentType) {
        return "text/plain".equals(contentType);
    }

    @Override
    public ProcessedDocument process(File file) {

        try {

            String content = Files.readString(file.toPath());

            return ProcessedDocument.builder()
                    .content(content)
                    .pageCount(1)
                    .characterCount(content.length())
                    .wordCount(content.split("\\s+").length)
                    .title(file.getName())
                    .language("UNKNOWN")
                    .metadata(new HashMap<>())
                    .build();

        } catch (Exception ex) {

            log.error("Unable to process txt", ex);

            throw new BusinessException("Unable to process text document.");

        }

    }
}