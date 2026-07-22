package com.rishi.aihub.features.ai.prompt.impl;

import com.rishi.aihub.features.ai.prompt.PromptBuilder;
import com.rishi.aihub.features.ai.prompt.template.PromptTemplate;
import com.rishi.aihub.features.vector.model.RetrievedChunk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PromptBuilderImpl implements PromptBuilder {

    private final PromptTemplate promptTemplate;

    @Override
    public String buildPrompt(
            List<Message> history,
            List<RetrievedChunk> documents,
            String question) {

        String context = buildContext(documents);
        String historyText = buildHistory(history);

        return promptTemplate.build(
                context,
                historyText,
                question
        );
    }

    private String buildContext(List<RetrievedChunk> documents) {

        if (documents == null || documents.isEmpty()) {
            return "No relevant documents found.";
        }

        StringBuilder builder = new StringBuilder();

        for (RetrievedChunk chunk : documents) {

            builder.append("==================================================\n");

            builder.append("Document : ")
                    .append(chunk.getDocumentName())
                    .append("\n");

            builder.append("Page     : ")
                    .append(chunk.getPageNumber())
                    .append("\n");

            builder.append("Chunk    : ")
                    .append(chunk.getChunkIndex())
                    .append("\n\n");

            builder.append(chunk.getContent());

            builder.append("\n\n");
        }

        builder.append("==================================================");

        return builder.toString();
    }

    private String buildHistory(List<Message> history) {

        if (history == null || history.isEmpty()) {
            return "No previous conversation.";
        }

        StringBuilder builder = new StringBuilder();

        for (Message message : history) {

            if (message instanceof UserMessage) {

                builder.append("User: ")
                        .append(message.getText())
                        .append("\n");

            } else if (message instanceof AssistantMessage) {

                builder.append("Assistant: ")
                        .append(message.getText())
                        .append("\n");

            } else {

                builder.append(message.getText())
                        .append("\n");
            }
        }

        return builder.toString();
    }
}