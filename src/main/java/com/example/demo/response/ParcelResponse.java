package com.example.demo.response;

import com.example.demo.entity.ParcelStatus;
import com.example.demo.entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParcelResponse {
    private Long id;

    private Long trackingCode;

    private String sender;

    private String receiver;

    private Double weight;

    private ParcelStatus status;

    private Warehouse warehouse;
}
