package com.example.demo.request;

import com.example.demo.entity.ParcelStatus;
import com.example.demo.entity.Warehouse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParcelRequest {

    @NotNull(message = "Parcel ID must not be null")
    @Min(value = 1, message = "Parcel ID must be greater than 0")
    private Long id;

    private Long trackingCode;

    private String sender;

    private String receiver;

    private Double weight;

    private ParcelStatus status;
}
