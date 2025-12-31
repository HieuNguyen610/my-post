package com.example.demo.controller;

import com.example.demo.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/staffs")
    public Map<String, Object> getStaffInfo() {
        Map<String, Object> info = new java.util.HashMap<>();
        info.put("message", "Staff info endpoint");
        info.put("status", "success");
        info.put("data", staffService.findAll());
        return info;
    }
}
