package com.rishi.aihub.features.auth.security;

import com.rishi.aihub.common.exception.UnauthorizedException;
import com.rishi.aihub.features.auth.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CurrentUserServiceImpl implements CurrentUserService {

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {

            throw new UnauthorizedException("No authenticated user found.");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserPrincipal userPrincipal)) {
            throw new UnauthorizedException("Invalid authenticated principal.");
        }

        return userPrincipal.getUser();
    }

    @Override
    public String getCurrentUserId() {
        return getCurrentUser().getId();
    }

    @Override
    public String getCurrentUserEmail() {
        return getCurrentUser().getEmail();
    }
}