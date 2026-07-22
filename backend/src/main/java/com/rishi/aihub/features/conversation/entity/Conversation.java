package com.rishi.aihub.features.conversation.entity;

import com.rishi.aihub.common.audit.AuditEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "conversations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conversation extends AuditEntity {

    @Id
    private String id;

    private String userId;

    // NEW
    private String knowledgeBaseId;

    private String title;
}