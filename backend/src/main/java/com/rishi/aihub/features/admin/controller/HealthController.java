package com.rishi.aihub.features.admin.controller;

import com.rishi.aihub.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping
    public ResponseEntity<ApiResponse<String>> health() {

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Application is healthy")
                        .data("Enterprise AI Knowledge Hub")
                        .build()
        );

    }
}
