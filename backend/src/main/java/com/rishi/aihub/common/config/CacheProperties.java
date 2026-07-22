package com.rishi.aihub.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.cache")
public class CacheProperties {

    private Conversation conversation = new Conversation();

    @Getter
    @Setter
    public static class Conversation {

        private Duration ttl = Duration.ofMinutes(30);

    }
}