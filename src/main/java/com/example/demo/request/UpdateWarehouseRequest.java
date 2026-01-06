package com.example.demo.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateWarehouseRequest implements Serializable {
    @NotNull(message = "Code cannot be null")
    private Long code;

    @NotBlank(message = "Address cannot be null or blank")
    private String address;

    @NotNull(message = "Capacity cannot be null")
    @Min(message = "Capacity must be at least 10", value = 10)
    private Integer capacity;
}
