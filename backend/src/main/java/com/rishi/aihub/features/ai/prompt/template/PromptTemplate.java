package com.rishi.aihub.features.ai.prompt.template;

public interface PromptTemplate {

    /**
     * Template identifier.
     */
    String getName();

    /**
     * Build the final prompt.
     */
    String build(
            String context,
            String history,
            String question);

}