package com.rishi.aihub.features.ai.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AIChatResult {

    /**
     * Final AI answer.
     */
    private String answer;

    /**
     * Documents used to generate the answer.
     */
    private List<SourceResponse> sources;

}