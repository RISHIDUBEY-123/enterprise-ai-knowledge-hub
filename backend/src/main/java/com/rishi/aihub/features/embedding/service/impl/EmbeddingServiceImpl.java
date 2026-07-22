package com.rishi.aihub.features.embedding.service.impl;

import com.rishi.aihub.features.embedding.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

    private final EmbeddingModel embeddingModel;

    @Override
    public List<Float> generateEmbedding(String text) {

        EmbeddingResponse response =
                embeddingModel.call(
                        new EmbeddingRequest(List.of(text), null));

        float[] embedding = response.getResult().getOutput();

        List<Float> vector = new ArrayList<>(embedding.length);

        for (float value : embedding) {
            vector.add(value);
        }

        return vector;
    }
}