package com.rishi.aihub.features.conversation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationSummaryResponse {

    private String id;

    @NotBlank
    private String title;

    private Instant updatedAt;

}