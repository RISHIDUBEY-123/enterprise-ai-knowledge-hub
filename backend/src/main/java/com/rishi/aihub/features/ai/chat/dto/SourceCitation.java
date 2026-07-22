package com.rishi.aihub.features.ai.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SourceCitation {

    private String documentId;

    private String documentName;

    private Integer chunkIndex;
}