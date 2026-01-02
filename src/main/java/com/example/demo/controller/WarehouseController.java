package com.example.demo.controller;

import com.example.demo.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
