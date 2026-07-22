package com.rishi.aihub.features.auth.security;

import com.rishi.aihub.features.auth.entity.User;

public interface CurrentUserService {

    User getCurrentUser();

    String getCurrentUserId();

    String getCurrentUserEmail();

}