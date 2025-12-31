package com.example.demo.service.impl;

import com.example.demo.repository.WarehouseRepository;
import com.example.demo.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
}
