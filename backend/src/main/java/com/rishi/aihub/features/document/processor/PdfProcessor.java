package com.rishi.aihub.features.document.processor;

import com.rishi.aihub.common.exception.BusinessException;
import com.rishi.aihub.features.document.model.ProcessedDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Component
@Slf4j
public class PdfProcessor implements DocumentProcessor {

    @Override
    public boolean supports(String contentType) {
        return "application/pdf".equals(contentType);
    }

    @Override
    public ProcessedDocument process(File file) {

        try (PDDocument document = Loader.loadPDF(file)) {

            PDFTextStripper stripper = new PDFTextStripper();

            String content = stripper.getText(document);

            return ProcessedDocument.builder()
                    .content(content)
                    .pageCount(document.getNumberOfPages())
                    .characterCount(content.length())
                    .wordCount(content.split("\\s+").length)
                    .title(file.getName())
                    .language("UNKNOWN")
                    .metadata(new HashMap<>())
                    .build();

        } catch (IOException ex) {

            log.error("Unable to process pdf", ex);

            throw new BusinessException("Unable to process PDF.");

        }

    }
}