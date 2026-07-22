package com.rishi.aihub.features.document.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadDocumentResponse {

    private String documentId;

    private String fileName;

}