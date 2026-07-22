package com.rishi.aihub.features.chunk.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Chunk {

    private Integer index;

    private String content;

}