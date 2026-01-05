package com.example.demo.controller;

import com.example.demo.response.ApiResponse;
import com.example.demo.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/staffs")
    public ApiResponse<?> getStaffInfo() {
        return ApiResponse.success("Staff info endpoint", staffService.findAll());
    }
}
