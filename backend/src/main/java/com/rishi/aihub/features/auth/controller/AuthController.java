package com.rishi.aihub.features.auth.controller;

import com.rishi.aihub.common.constants.ApiVersion;
import com.rishi.aihub.common.response.ApiResponse;
import com.rishi.aihub.common.response.ResponseBuilder;
import com.rishi.aihub.features.auth.dto.request.LoginRequest;
import com.rishi.aihub.features.auth.dto.request.RegisterRequest;
import com.rishi.aihub.features.auth.dto.response.AuthResponse;
import com.rishi.aihub.features.auth.dto.response.UserProfileResponse;
import com.rishi.aihub.features.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiVersion.API + "/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        response,
                        "Login successful."
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserProfileResponse>> me() {

        return ResponseEntity.ok(
                ResponseBuilder.success(
                        authService.getCurrentUser(),
                        "Current user fetched successfully."
                )
        );
    }

}