package com.example.demo.service.impl;

import com.example.demo.repository.StaffRepository;
import com.example.demo.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
}
