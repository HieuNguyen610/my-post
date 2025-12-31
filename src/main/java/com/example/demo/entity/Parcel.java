package com.example.demo.entity;

import jakarta.persistence.*;

// kiện hàng
@Entity
@Table(name = "parcels")

public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long trackingCode;

    private String sender;

    private String receiver;

    private Double weight;

    private ParcelStatus status;

    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
}
