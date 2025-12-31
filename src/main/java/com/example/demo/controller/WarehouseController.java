package com.example.demo.controller;

import com.example.demo.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @GetMapping("/warehouses")
    public ResponseEntity<?> getWarehouseInfo() {
        return ResponseEntity.ok(warehouseService.findAll());
    }
}
