package com.rishi.aihub.common.response;

import java.util.List;

public final class ResponseBuilder {

    private ResponseBuilder() {
    }

    public static <T> ApiResponse<T> success(T data, String message) {

        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();

    }

    public static <T> ApiResponse<T> failure(String message) {

        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .errors(List.of(message))
                .build();

    }

}