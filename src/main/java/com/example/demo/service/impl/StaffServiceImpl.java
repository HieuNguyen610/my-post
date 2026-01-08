package com.example.demo.service.impl;

import com.example.demo.entity.Staff;
import com.example.demo.repository.StaffRepository;
import com.example.demo.request.CreateStaffRequest;
import com.example.demo.response.StaffResponse;
import com.example.demo.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j(topic = "STAFF_SERVICE")
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;

    @Override
    public List<StaffResponse> findAll() {
        return staffRepository.findAll().stream().map
                (staff -> StaffResponse.builder()
                .id(staff.getId())
                .username(staff.getUsername())
                .email(staff.getEmail())
                .phone(staff.getPhone())
                .role(staff.getRole())
                .dateOfBirth(staff.getDateOfBirth())
                .build()).toList();
    }

    @Override
    public StaffResponse createStaff(CreateStaffRequest request) {
        staffRepository.findByEmail(request.getEmail()).ifPresent(s -> {
            throw new IllegalArgumentException("Staff with email " + request.getEmail() + " already exists.");
        });
        Staff newStaff = Staff.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        log.info("Creating new staff with email: {}", request.getEmail());
        Staff savedStaff = staffRepository.save(newStaff);

        return StaffResponse.builder()
                .id(savedStaff.getId())
                .username(savedStaff.getUsername())
                .email(savedStaff.getEmail())
                .phone(savedStaff.getPhone())
                .role(savedStaff.getRole())
                .dateOfBirth(savedStaff.getDateOfBirth())
                .build();
    }
}
