package com.example.demo.controller;

import com.example.demo.request.CreateStaffRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.response.StaffResponse;
import com.example.demo.service.StaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/staffs")
    public ApiResponse<?> getStaffInfo() {
        return ApiResponse.success("Staff info endpoint", staffService.findAll());
    }

    @PostMapping("/staffs/create")
    public ResponseEntity<ApiResponse<StaffResponse>> createStaff(@Valid @RequestBody CreateStaffRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Staff created successfully", staffService.createStaff(request)));
    }
}
