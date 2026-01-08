package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "shipments")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shipmentCode;

    private String senderName;

    private String senderPhone;

    private String receiverName;

    private String receiverPhone;

    private String pickupAddress;

    private String deliveryAddress;

    private ShipmentStatus currentStatus;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isDeleted;
}
