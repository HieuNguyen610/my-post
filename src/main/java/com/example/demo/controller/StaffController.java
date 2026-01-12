package com.example.demo.controller;

import com.example.demo.entity.ApiMessage;
import com.example.demo.request.CreateStaffRequest;
import com.example.demo.request.StaffSearchRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.StaffPageResponse;
import com.example.demo.response.StaffResponse;
import com.example.demo.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Staff", description = "Operations about staff management")
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/staffs")
    @Operation(summary = "Get all staffs", description = "Returns list of all staff members")
    public ApiResponse<?> getStaffInfo() {
        return ApiResponse.success(ApiMessage.USER_CREATED.getMessage(), staffService.findAll());
    }

    @PostMapping("/staffs/create")
    @Operation(summary = "Create staff", description = "Create a new staff member")
    public ResponseEntity<ApiResponse<StaffResponse>> createStaff(@Valid @RequestBody CreateStaffRequest request) {
        return ResponseEntity.ok(ApiResponse.success(ApiMessage.USER_CREATED.getMessage(), staffService.createStaff(request)));
    }

    @GetMapping("/staffs/search")
    @Operation(summary = "Search staffs", description = "Search staff by keyword with pagination")
    public ResponseEntity<ApiResponse<StaffPageResponse>> searchStaffs(@Valid @RequestBody StaffSearchRequest request) {
        StaffPageResponse result = staffService.searchStaffs(request);
        return ResponseEntity.ok(ApiResponse.success(ApiMessage.USER_SEARCHED.getMessage(), result));
    }
}
