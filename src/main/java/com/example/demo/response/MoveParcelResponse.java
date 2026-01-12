package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoveParcelResponse implements Serializable {
    private String message;
    private Long parcelId;
    private Long newWarehouseId;
}
