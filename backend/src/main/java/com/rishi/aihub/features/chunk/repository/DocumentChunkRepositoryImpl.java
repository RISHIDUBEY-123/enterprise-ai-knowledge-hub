package com.rishi.aihub.features.chunk.repository;

import com.rishi.aihub.features.chunk.entity.DocumentChunk;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DocumentChunkRepositoryImpl
        implements DocumentChunkRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<DocumentChunk> keywordSearch(
            String knowledgeBaseId,
            String query,
            int limit) {

        TextCriteria textCriteria =
                TextCriteria.forDefaultLanguage()
                        .matching(query);

        Query mongoQuery = new Query();

        mongoQuery.addCriteria(
                Criteria.where("knowledgeBaseId")
                        .is(knowledgeBaseId)
        );

        mongoQuery.addCriteria(textCriteria);

        mongoQuery.limit(limit);

        return mongoTemplate.find(
                mongoQuery,
                DocumentChunk.class
        );
    }
}
