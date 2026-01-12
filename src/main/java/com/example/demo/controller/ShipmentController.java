package com.example.demo.controller;

import com.example.demo.service.ShipmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shipments")
@Tag(name = "Shipment", description = "Operations about shipment management")
public class ShipmentController {

    private final ShipmentService shipmentService;
}
