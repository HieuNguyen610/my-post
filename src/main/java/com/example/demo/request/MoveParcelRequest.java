package com.example.demo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveParcelRequest {
    @NotNull(message = "Parcel ID must not be null")
    private Long parcelId;
    @NotNull(message = "Destination Warehouse ID must not be null")
    private Long destinationWarehouseId;
}
