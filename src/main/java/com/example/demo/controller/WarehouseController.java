package com.example.demo.controller;

import com.example.demo.entity.Warehouse;
import com.example.demo.entity.ApiMessage;
import com.example.demo.request.CreateWarehouseRequest;
import com.example.demo.request.UpdateWarehouseRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.WarehouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouses")
    public ResponseEntity<ApiResponse<List<Warehouse>>> getWarehouseInfo() {
        return ResponseEntity.ok(ApiResponse.success(ApiMessage.WAREHOUSE_BY_ID.getMessage(), warehouseService.findAll()));
    }

    @GetMapping("/warehouses/{id}")
    public ResponseEntity<ApiResponse<Warehouse>> getWarehouseById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(ApiMessage.WAREHOUSE_BY_ID.getMessage(), warehouseService.findById(id)));
    }

    @PostMapping("/warehouses/create")
    public ResponseEntity<ApiResponse<Warehouse>> createWarehouse(@Valid @RequestBody CreateWarehouseRequest request) {
        return ResponseEntity.ok(ApiResponse.success(ApiMessage.WAREHOUSE_CREATED.getMessage(), warehouseService.createWarehouse(request)));
    }

    @PutMapping("/warehouses/update")
    public ResponseEntity<ApiResponse<Warehouse>> updateWarehouse(@Valid @RequestBody UpdateWarehouseRequest request) {
        return ResponseEntity.ok(ApiResponse.success(ApiMessage.WAREHOUSE_UPDATED.getMessage(), warehouseService.updateWarehouse(request)));
    }
}
