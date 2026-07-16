package com.rishi.aihub.features.auth.service;

import com.rishi.aihub.features.auth.dto.request.LoginRequest;
import com.rishi.aihub.features.auth.dto.request.RegisterRequest;
import com.rishi.aihub.features.auth.dto.response.AuthResponse;
import com.rishi.aihub.features.auth.dto.response.UserProfileResponse;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
    UserProfileResponse getCurrentUser();

}