package com.rishi.aihub.features.auth.entity;

import com.rishi.aihub.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity {

    private String firstName;

    private String lastName;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String phoneNumber;

    private String profileImage;

    @Builder.Default
    private UserRole role = UserRole.USER;

    @Builder.Default
    private boolean enabled = true;

    @Builder.Default
    private boolean accountNonLocked = true;

    @Builder.Default
    private boolean accountNonExpired = true;

    @Builder.Default
    private boolean credentialsNonExpired = true;

    private LocalDateTime lastLogin;
}