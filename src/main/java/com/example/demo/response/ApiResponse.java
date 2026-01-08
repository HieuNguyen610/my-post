package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response wrapper used across controllers to avoid repeating literal keys.
 * Contains message, status and data fields.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    private String message;
    private String status;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .status("success")
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> failure(String message, String status) {
        return ApiResponse.<T>builder()
                .message(message)
                .status(status)
                .build();
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .status("error")
                .data(data)
                .build();
    }
}

