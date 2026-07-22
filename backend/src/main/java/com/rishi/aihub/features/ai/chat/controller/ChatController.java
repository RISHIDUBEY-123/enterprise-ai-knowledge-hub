package com.rishi.aihub.features.ai.chat.controller;

import com.rishi.aihub.common.constants.ApiVersion;
import com.rishi.aihub.common.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(ApiVersion.API + "/ai")
@RequiredArgsConstructor
public class ChatController {

    /**
     * AI Module Health Check
     */
    @GetMapping("/health")
    public ResponseEntity<BaseResponse<Map<String, String>>> health() {

        return ResponseEntity.ok(
                BaseResponse.success(
                        Map.of(
                                "module", "AI Chat",
                                "status", "UP"
                        ),
                        "AI module is running."
                )
        );
    }

}