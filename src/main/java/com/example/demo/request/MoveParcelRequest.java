package com.example.demo.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveParcelRequest implements Serializable {
    @NotNull(message = "Parcel ID must not be null")
    private Long parcelId;
    @NotNull(message = "Destination Warehouse ID must not be null")
    private Long destinationWarehouseId;
}
