package com.example.demo.service;

import com.example.demo.entity.Warehouse;
import com.example.demo.request.CreateWarehouseRequest;
import com.example.demo.response.WarehouseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WarehouseService {
    List<Warehouse> findAll();

    Warehouse findById(Long id);

    Warehouse createWarehouse(CreateWarehouseRequest request);
}
