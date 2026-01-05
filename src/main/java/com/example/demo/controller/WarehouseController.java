package com.example.demo.controller;

import com.example.demo.request.CreateWarehouseRequest;
import com.example.demo.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouses")
    public ResponseEntity<?> getWarehouseInfo() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", "Warehouse info endpoint");
        map.put("status", "success");
        map.put("data", warehouseService.findAll());
        return ResponseEntity.ok(map);
    }

    @GetMapping("/warehouses/{id}")
    public ResponseEntity<?> getWarehouseById(@PathVariable Long id) {
        // Implementation for fetching warehouse by ID
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", "Warehouse by ID endpoint");
        map.put("status", "success");
        map.put("data", warehouseService.findById(id)); // Replace null with actual data
        return ResponseEntity.ok(map);
    }

    @PostMapping("/warehouses/create")
    public Map<String, Object> createWarehouse(@Valid @RequestBody CreateWarehouseRequest request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Warehouse created successfully");
        response.put("status", "success");
        response.put("data", warehouseService.createWarehouse(request)); // Replace null with actual data
        return response;
    }

}
