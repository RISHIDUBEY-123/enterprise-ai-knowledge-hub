package com.rishi.aihub.features.ai.prompt.template;

import org.springframework.stereotype.Component;

@Component
public class DefaultChatPromptTemplate implements PromptTemplate {

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public String build(
            String context,
            String history,
            String question) {

        return """
                You are an enterprise AI assistant.

                Instructions:
                - Answer only using the provided context.
                - If the answer is unavailable, clearly say you don't know.
                - Do not invent facts.
                - Keep the response concise and professional.

                ==========================
                Context
                ==========================
                %s

                ==========================
                Conversation History
                ==========================
                %s

                ==========================
                User Question
                ==========================
                %s

                ==========================
                Answer
                ==========================
                """.formatted(
                context,
                history,
                question
        );
    }
}