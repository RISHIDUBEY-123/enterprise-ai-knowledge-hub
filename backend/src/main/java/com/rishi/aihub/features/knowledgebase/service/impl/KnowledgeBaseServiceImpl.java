package com.rishi.aihub.features.knowledgebase.service.impl;

import com.rishi.aihub.common.exception.ResourceNotFoundException;
import com.rishi.aihub.features.auth.security.CurrentUserService;
import com.rishi.aihub.features.knowledgebase.dto.request.CreateKnowledgeBaseRequest;
import com.rishi.aihub.features.knowledgebase.dto.response.KnowledgeBaseResponse;
import com.rishi.aihub.features.knowledgebase.entity.KnowledgeBase;
import com.rishi.aihub.features.knowledgebase.repository.KnowledgeBaseRepository;
import com.rishi.aihub.features.knowledgebase.service.KnowledgeBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final KnowledgeBaseRepository knowledgeBaseRepository;
    private final CurrentUserService currentUserService;

    @Override
    public KnowledgeBaseResponse create(CreateKnowledgeBaseRequest request) {

        KnowledgeBase knowledgeBase = KnowledgeBase.builder()
                .ownerId(currentUserService.getCurrentUserId())
                .name(request.getName())
                .description(request.getDescription())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        KnowledgeBase saved = knowledgeBaseRepository.save(knowledgeBase);

        log.info("Knowledge Base created: {}", saved.getId());

        return mapToResponse(saved);
    }

    @Override
    public List<KnowledgeBaseResponse> getAll() {

        return knowledgeBaseRepository
                .findByOwnerIdOrderByCreatedAtDesc(currentUserService.getCurrentUserId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public KnowledgeBaseResponse getById(String id) {

        KnowledgeBase kb = knowledgeBaseRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("KnowledgeBase", id));

        validateOwnership(kb);

        return mapToResponse(kb);
    }

    @Override
    public void delete(String id) {

        KnowledgeBase kb = knowledgeBaseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("KnowledgeBase", id));

        validateOwnership(kb);

        knowledgeBaseRepository.delete(kb);

        log.info("Knowledge Base deleted: {}", id);
    }

    private void validateOwnership(KnowledgeBase kb) {

        if (!kb.getOwnerId().equals(currentUserService.getCurrentUserId())) {
            throw new AccessDeniedException("You are not authorized to access this Knowledge Base.");
        }
    }

    private KnowledgeBaseResponse mapToResponse(KnowledgeBase kb) {

        return KnowledgeBaseResponse.builder()
                .id(kb.getId())
                .name(kb.getName())
                .description(kb.getDescription())
                .createdAt(kb.getCreatedAt())
                .build();
    }
}