package com.rishi.aihub.features.cache.constants;

public final class CacheKeys {

    private CacheKeys() {
    }

    public static String conversationMessages(String conversationId) {
        return "conversation:messages:" + conversationId;
    }
}