package com.rishi.aihub.features.auth.mapper;

import com.rishi.aihub.features.auth.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public static class AuthResponse {
        public static class UserDto {
            private String id;
            private String username;
            private String email;
            private Boolean active;
            private Boolean emailVerified;

            // Getters
            public String getId() { return id; }
            public String getUsername() { return username; }
            public String getEmail() { return email; }
            public Boolean getActive() { return active; }
            public Boolean getEmailVerified() { return emailVerified; }

            public static UserDtoBuilder builder() {
                return new UserDtoBuilder();
            }

            public static class UserDtoBuilder {
                private String id;
                private String username;
                private String email;
                private Boolean active;
                private Boolean emailVerified;

                public UserDtoBuilder id(String id) { this.id = id; return this; }
                public UserDtoBuilder username(String username) { this.username = username; return this; }
                public UserDtoBuilder email(String email) { this.email = email; return this; }
                public UserDtoBuilder active(Boolean active) { this.active = active; return this; }
                public UserDtoBuilder emailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; return this; }
                public UserDto build() {
                    UserDto dto = new UserDto();
                    dto.id = this.id;
                    dto.username = this.username;
                    dto.email = this.email;
                    dto.active = this.active;
                    dto.emailVerified = this.emailVerified;
                    return dto;
                }
            }
        }
    }

    public AuthResponse.UserDto toUserDto(User user) {
        return AuthResponse.UserDto.builder()
                .id(user.getId())
                .username("")  // TODO: Update with actual getter when User entity is defined
                .email(user.getEmail())
                .active(false)  // TODO: Update with actual getter when User entity is defined
                .emailVerified(false)  // TODO: Update with actual getter when User entity is defined
                .build();
    }
}
