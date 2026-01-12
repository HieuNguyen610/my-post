package com.example.demo.service;

import com.example.demo.request.CreateStaffRequest;
import com.example.demo.request.StaffSearchRequest;
import com.example.demo.response.StaffPageResponse;
import com.example.demo.response.StaffResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface StaffService {
    List<StaffResponse> findAll();

    StaffResponse createStaff(@Valid CreateStaffRequest request);

    StaffPageResponse searchStaffs(StaffSearchRequest request);
}
