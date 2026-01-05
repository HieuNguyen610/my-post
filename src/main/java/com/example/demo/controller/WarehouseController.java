package com.example.demo.controller;

import com.example.demo.request.CreateWarehouseRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouses")
    public ResponseEntity<ApiResponse<?>> getWarehouseInfo() {
        return ResponseEntity.ok(ApiResponse.success("Warehouse info endpoint", warehouseService.findAll()));
    }

    @GetMapping("/warehouses/{id}")
    public ResponseEntity<ApiResponse<?>> getWarehouseById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Warehouse by ID endpoint", warehouseService.findById(id)));
    }

    @PostMapping("/warehouses/create")
    public ResponseEntity<ApiResponse<?>> createWarehouse(@Valid @RequestBody CreateWarehouseRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Warehouse created successfully", warehouseService.createWarehouse(request)));
    }

}
