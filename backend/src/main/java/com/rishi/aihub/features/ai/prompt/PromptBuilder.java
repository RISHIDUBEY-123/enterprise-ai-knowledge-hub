package com.rishi.aihub.features.ai.prompt;

import com.rishi.aihub.features.vector.model.RetrievedChunk;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

public interface PromptBuilder {

    String buildPrompt(
            List<Message> history,
            List<RetrievedChunk> documents,
            String question);

}