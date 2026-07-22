package com.rishi.aihub.features.document.validator;

import com.rishi.aihub.common.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Component
public class FileValidator {

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "text/plain"
    );

    public void validate(MultipartFile file) {

        if (file.isEmpty()) {
            throw new BusinessException("File cannot be empty.");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new BusinessException(
                    "Unsupported file type: " + file.getContentType()
            );
        }
    }
}