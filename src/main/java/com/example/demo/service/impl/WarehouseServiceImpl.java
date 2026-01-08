package com.example.demo.service.impl;

import com.example.demo.entity.Warehouse;
import com.example.demo.exception.WarehouseNotFoundException;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.request.CreateWarehouseRequest;
import com.example.demo.request.UpdateWarehouseRequest;
import com.example.demo.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "WAREHOUSE_SERVICE")
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    @Override
    public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

    @Override
    @Cacheable(value = "warehouses", key = "#id")
    public Warehouse findById(Long id) {
        log.info("Fetching warehouse with id: {}", id);
        return warehouseRepository.findById(id).orElseThrow
                ( () -> new WarehouseNotFoundException("Warehouse not found with id: " + id));
    }

    @Override
    public Warehouse createWarehouse(CreateWarehouseRequest request) {
        warehouseRepository.findByCode(request.getCode()).ifPresent( w -> {
            throw new IllegalArgumentException("Warehouse with code " + request.getCode() + " already exists.");
        });

        log.info("Creating new warehouse with code: {}", request.getCode());
        Warehouse warehouse = Warehouse.builder()
                .code(request.getCode())
                .address(request.getAddress())
                .capacity(request.getCapacity())
                .build();
        return warehouseRepository.save(warehouse);
    }

    @Override
    public Warehouse updateWarehouse(UpdateWarehouseRequest request) {
        Warehouse warehouse = warehouseRepository.findByCode(request.getCode()).orElseThrow(
                () -> new WarehouseNotFoundException("Warehouse not found with code: " + request.getCode())
        );
        warehouse.setAddress(request.getAddress());
        warehouse.setCapacity(request.getCapacity());
        log.info("Updating warehouse with code: {}", request.getCode());
        return warehouseRepository.save(warehouse);
    }
}

