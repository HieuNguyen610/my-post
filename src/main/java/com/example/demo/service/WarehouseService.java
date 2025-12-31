package com.example.demo.service;

import com.example.demo.entity.Warehouse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface WarehouseService {
    List<Warehouse> findAll();
}
