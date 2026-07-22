package com.rishi.aihub.features.ai.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SourceResponse {

    private String documentName;

    private Integer pageNumber;

    private Integer chunkIndex;

    private Double score;

}