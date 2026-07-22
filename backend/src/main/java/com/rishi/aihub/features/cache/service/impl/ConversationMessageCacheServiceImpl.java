package com.rishi.aihub.features.cache.service.impl;

import com.rishi.aihub.common.config.CacheProperties;
import com.rishi.aihub.features.cache.constants.CacheKeys;
import com.rishi.aihub.features.cache.service.ConversationMessageCacheService;
import com.rishi.aihub.features.conversation.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationMessageCacheServiceImpl
        implements ConversationMessageCacheService {

    /**
     * Cache conversation messages for 30 minutes.
     */
    private final CacheProperties cacheProperties;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    @SuppressWarnings("unchecked")
    public Optional<List<MessageResponse>> get(String conversationId) {

        String key = CacheKeys.conversationMessages(conversationId);

        try {

            Object cachedValue = redisTemplate.opsForValue().get(key);

            if (cachedValue == null) {

                log.debug(
                        "Redis cache miss for conversation {}",
                        conversationId);

                return Optional.empty();
            }

            log.debug(
                    "Redis cache hit for conversation {}",
                    conversationId);

            return Optional.of((List<MessageResponse>) cachedValue);

        } catch (DataAccessException ex) {

            log.warn("Redis unavailable while reading cache for conversation {}",
                    conversationId,
                    ex);

            return Optional.empty();
        }
    }

    @Override
    public void put(String conversationId,
                    List<MessageResponse> messages) {

        String key = CacheKeys.conversationMessages(conversationId);

        try {

            redisTemplate.opsForValue().set(
                    key,
                    messages,
                    cacheProperties.getConversation().getTtl()
            );

            log.debug("Conversation messages cached successfully : {}", key);

        } catch (DataAccessException ex) {

            log.warn("Unable to cache conversation {}",
                    conversationId,
                    ex);
        }
    }

    @Override
    public void evict(String conversationId) {

        String key = CacheKeys.conversationMessages(conversationId);

        try {

            Boolean deleted = redisTemplate.delete(key);

            if (Boolean.TRUE.equals(deleted)) {

                log.debug("Conversation cache evicted : {}", key);

            } else {

                log.debug("Conversation cache not present : {}", key);
            }

        } catch (DataAccessException ex) {

            log.warn("Unable to evict conversation cache {}",
                    conversationId,
                    ex);
        }
    }
}