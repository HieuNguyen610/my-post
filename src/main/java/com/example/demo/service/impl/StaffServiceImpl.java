package com.example.demo.service.impl;

import com.example.demo.entity.Staff;
import com.example.demo.repository.StaffRepository;
import com.example.demo.request.CreateStaffRequest;
import com.example.demo.request.StaffSearchRequest;
import com.example.demo.request.UpdateStaffRequest;
import com.example.demo.response.StaffPageResponse;
import com.example.demo.response.StaffResponse;
import com.example.demo.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                (StaffResponse::fromEntity).toList();
    }

    @Override
    public StaffResponse createStaff(CreateStaffRequest request) {
        staffRepository.findByEmail(request.getEmail()).ifPresent(s -> {
            throw new IllegalArgumentException("Staff with email " + request.getEmail() + " already exists.");
        });
        Staff newStaff = CreateStaffRequest.toStaff(request);
        newStaff.setPassword(request.getPassword());

        log.info("Creating new staff with email: {}", request.getEmail());
        Staff savedStaff = staffRepository.save(newStaff);

        return StaffResponse.fromEntity(savedStaff);
    }

    @Override
    public StaffPageResponse searchStaffs(StaffSearchRequest request) {
        // Validate and normalize paging parameters
        int page = Math.max(request.getPage(), 0);
        int size = request.getSize() <= 0 ? 10 : Math.min(request.getSize(), 100);

        // Normalize keyword: treat empty/blank as null to match repository query behavior
        String keyword = request.getKeyword() == null || request.getKeyword().trim().isEmpty()
                ? null
                : request.getKeyword().trim();

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));

        Page<Staff> staffPage = staffRepository.searchByKeyword(keyword, pageable);

        List<StaffResponse> data = staffPage.getContent().stream().map(StaffResponse::fromEntity).toList();

        return StaffPageResponse.builder()
                .page(page)
                .size(size)
                .totalElements(staffPage.getTotalElements())
                .totalPages(staffPage.getTotalPages())
                .staffData(data)
                .build();
    }

    @Override
    public StaffResponse updateStaff(UpdateStaffRequest request) {
        Staff existingStaff = staffRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalArgumentException("Staff with id " + request.getId() + " not found."));
        // Update fields
        existingStaff.setUsername(request.getUsername());
        existingStaff.setEmail(request.getEmail());
        existingStaff.setPhone(request.getPhone());
        existingStaff.setDateOfBirth(request.getDateOfBirth());
        existingStaff.setRole(request.getRole());

        Staff updatedStaff = staffRepository.save(existingStaff);
        return StaffResponse.fromEntity(updatedStaff);
    }
}
