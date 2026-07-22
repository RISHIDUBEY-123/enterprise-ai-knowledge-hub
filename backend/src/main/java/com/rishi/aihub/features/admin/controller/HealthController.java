package com.rishi.aihub.features.admin.controller;

import com.rishi.aihub.common.constants.ApiVersion;
import com.rishi.aihub.common.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping(ApiVersion.API + "/health")
    public ResponseEntity<BaseResponse<String>> health() {

        return ResponseEntity.ok(
                BaseResponse.success(
                        "Enterprise AI Knowledge Hub",
                        "Application is running successfully"
                )
        );

    }

}