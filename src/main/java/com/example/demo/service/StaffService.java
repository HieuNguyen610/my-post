package com.example.demo.service;

import com.example.demo.request.CreateStaffRequest;
import com.example.demo.response.StaffResponse;
import jakarta.validation.Valid;

public interface StaffService {
    Object findAll();

    StaffResponse createStaff(@Valid CreateStaffRequest request);
}
