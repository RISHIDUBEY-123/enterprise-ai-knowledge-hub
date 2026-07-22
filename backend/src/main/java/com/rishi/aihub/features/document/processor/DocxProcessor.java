package com.rishi.aihub.features.document.processor;

import com.rishi.aihub.common.exception.BusinessException;
import com.rishi.aihub.features.document.model.ProcessedDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

@Component
@Slf4j
public class DocxProcessor implements DocumentProcessor {

    @Override
    public boolean supports(String contentType) {

        return "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                .equals(contentType);

    }

    @Override
    public ProcessedDocument process(File file) {

        try (FileInputStream fis = new FileInputStream(file);
             XWPFDocument document = new XWPFDocument(fis);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {

            String content = extractor.getText();

            return ProcessedDocument.builder()
                    .content(content)
                    .pageCount(document.getProperties()
                            .getExtendedProperties()
                            .getUnderlyingProperties()
                            .getPages())
                    .characterCount(content.length())
                    .wordCount(content.split("\\s+").length)
                    .title(file.getName())
                    .language("UNKNOWN")
                    .metadata(new HashMap<>())
                    .build();

        } catch (Exception ex) {

            log.error("Unable to process docx", ex);

            throw new BusinessException("Unable to process DOCX.");

        }

    }
}