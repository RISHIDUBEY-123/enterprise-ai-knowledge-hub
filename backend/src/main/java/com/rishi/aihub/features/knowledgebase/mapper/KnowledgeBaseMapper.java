package com.rishi.aihub.features.knowledgebase.mapper;

import com.rishi.aihub.features.knowledgebase.dto.response.KnowledgeBaseResponse;
import com.rishi.aihub.features.knowledgebase.entity.KnowledgeBase;
import org.springframework.stereotype.Component;

@Component
public class KnowledgeBaseMapper {

    public KnowledgeBaseResponse toResponse(KnowledgeBase kb) {

        return KnowledgeBaseResponse.builder()
                .id(kb.getId())
                .name(kb.getName())
                .description(kb.getDescription())
                .createdAt(kb.getCreatedAt())
                .build();
    }
}