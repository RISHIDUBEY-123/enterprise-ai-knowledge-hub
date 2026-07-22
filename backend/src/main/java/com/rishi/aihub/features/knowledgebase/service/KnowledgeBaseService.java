package com.rishi.aihub.features.knowledgebase.service;

import com.rishi.aihub.features.knowledgebase.dto.request.CreateKnowledgeBaseRequest;
import com.rishi.aihub.features.knowledgebase.dto.response.KnowledgeBaseResponse;

import java.util.List;

public interface KnowledgeBaseService {

    KnowledgeBaseResponse create(CreateKnowledgeBaseRequest request);

    List<KnowledgeBaseResponse> getAll();

    KnowledgeBaseResponse getById(String id);

    void delete(String id);
}