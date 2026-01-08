package com.example.demo.service.impl;

import com.example.demo.repository.ShipmentRepository;
import com.example.demo.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
}
