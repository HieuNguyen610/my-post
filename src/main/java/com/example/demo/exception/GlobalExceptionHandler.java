package com.example.demo.exception;

import com.example.demo.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ApiResponse<Object> body(String message, String errorKey) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("errorKey", errorKey);
        return ApiResponse.error(message, map);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + (f.getDefaultMessage() == null ? "" : f.getDefaultMessage()))
                .toList();
        String message = "Validation failed: " + String.join("; ", errors);
        logger.info("Validation error: {}", message);
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("errorKey", "VALIDATION_ERROR");
        data.put("details", errors);
        ApiResponse<Object> body = ApiResponse.error(message, data);
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(AccessDeniedException ex) {
        logger.warn("Access denied: {}", ex.getMessage());
        ApiResponse<Object> body = body("Access is denied", "ACCESS_DENIED");
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiResponse<Object>> handleAuthentication(AuthenticationException ex) {
        logger.warn("Authentication failed: {}", ex.getMessage());
        ApiResponse<Object> body = body("Authentication failed", "AUTHENTICATION_FAILED");
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = "Method not allowed: " + ex.getMethod();
        logger.info(message);
        ApiResponse<Object> body = body(message, "METHOD_NOT_ALLOWED");
        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAll(Exception ex) {
        logger.error("Unhandled exception", ex);
        String message = ex.getMessage() == null ? "Internal server error" : ex.getMessage();
        ApiResponse<Object> body = body(message, "INTERNAL_ERROR");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
