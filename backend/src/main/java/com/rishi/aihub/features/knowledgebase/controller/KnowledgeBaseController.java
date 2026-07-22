package com.rishi.aihub.features.knowledgebase.controller;

import com.rishi.aihub.features.knowledgebase.dto.request.CreateKnowledgeBaseRequest;
import com.rishi.aihub.features.knowledgebase.dto.response.KnowledgeBaseResponse;
import com.rishi.aihub.features.knowledgebase.service.KnowledgeBaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/knowledge-bases")
@RequiredArgsConstructor
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KnowledgeBaseResponse create(
            @Valid @RequestBody CreateKnowledgeBaseRequest request) {

        return knowledgeBaseService.create(request);
    }

    @GetMapping
    public List<KnowledgeBaseResponse> getAll() {

        return knowledgeBaseService.getAll();
    }

    @GetMapping("/{id}")
    public KnowledgeBaseResponse getById(
            @PathVariable String id) {

        return knowledgeBaseService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String id) {

        knowledgeBaseService.delete(id);
    }
}
