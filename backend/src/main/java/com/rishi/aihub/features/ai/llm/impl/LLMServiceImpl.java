package com.rishi.aihub.features.ai.llm.impl;

import com.rishi.aihub.features.ai.llm.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LLMServiceImpl implements LLMService {

        private final ChatClient chatClient;

        @Override
        public String generate(String prompt) {
            return chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();
        }

}
