package com.rishi.aihub.features.auth.service.impl;

import com.rishi.aihub.common.exception.BusinessException;
import com.rishi.aihub.features.auth.dto.request.LoginRequest;
import com.rishi.aihub.features.auth.dto.request.RegisterRequest;
import com.rishi.aihub.features.auth.dto.response.AuthResponse;
import com.rishi.aihub.features.auth.dto.response.UserProfileResponse;
import com.rishi.aihub.features.auth.entity.User;
import com.rishi.aihub.features.auth.entity.UserRole;
import com.rishi.aihub.features.auth.repository.UserRepository;
import com.rishi.aihub.features.auth.security.JwtService;
import com.rishi.aihub.features.auth.security.UserPrincipal;
import com.rishi.aihub.features.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered.");
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BusinessException("Invalid email or password"));

        String token = jwtService.generateToken(
                new UserPrincipal(user)
        );

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return AuthResponse.builder()
                .accessToken(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
    @Override
    public UserProfileResponse getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        UserPrincipal principal =
                (UserPrincipal) authentication.getPrincipal();

        User user = principal.getUser();

        return UserProfileResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

}