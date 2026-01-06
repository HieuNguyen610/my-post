package com.example.demo.exception;

import com.example.demo.response.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ParcelNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleParcelNotFound(ParcelNotFoundException ex) {
        log.warn("Parcel not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage(), "error"));
    }

    @ExceptionHandler(WarehouseNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleWarehouseNotFound(WarehouseNotFoundException ex) {
        log.warn("Warehouse not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage(), "error"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        String message = "Validation failed: " + String.join("; ", details);

        Map<String, Object> data = new HashMap<>();
        data.put("errorKey", "VALIDATION_ERROR");
        data.put("details", details);

        log.debug("Validation errors: {}", details);

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(message, data));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> details = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.toList());

        String message = "Validation failed: " + String.join("; ", details);

        Map<String, Object> data = new HashMap<>();
        data.put("errorKey", "VALIDATION_ERROR");
        data.put("details", details);

        log.debug("Constraint violations: {}", details);

        return ResponseEntity.badRequest().body(ApiResponse.error(message, data));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        ex.getMostSpecificCause();
        String cause = ex.getMostSpecificCause().getMessage();
        String message = "Malformed request: " + cause;

        Map<String, Object> data = new HashMap<>();
        data.put("errorKey", "VALIDATION_ERROR");
        data.put("details", List.of(cause));

        log.debug("HttpMessageNotReadable: {}", cause);

        return ResponseEntity.badRequest().body(ApiResponse.error(message, data));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAll(Exception ex) {
        log.error("Unhandled exception: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("Internal server error: " + ex.getMessage(), "error"));
    }
}

