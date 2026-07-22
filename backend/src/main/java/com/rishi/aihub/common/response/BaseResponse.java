package com.rishi.aihub.common.response;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {

    private Instant timestamp;

    private boolean success;

    private String message;

    private T data;

    public static <T> BaseResponse<T> success(T data, String message) {
        return BaseResponse.<T>builder()
                .timestamp(Instant.now())
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> BaseResponse<T> failure(String message) {
        return BaseResponse.<T>builder()
                .timestamp(Instant.now())
                .success(false)
                .message(message)
                .build();
    }
}