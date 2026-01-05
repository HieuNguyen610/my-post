package com.example.demo.controller;

import com.example.demo.entity.Parcel;
import com.example.demo.request.MoveParcelRequest;
import com.example.demo.request.UpdateParcelRequest;
import com.example.demo.response.ApiResponse;
import com.example.demo.service.ParcelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping("/parcels")
    public ApiResponse<List<Parcel>> getParcels() {
        return ApiResponse.success("Parcels retrieved successfully", parcelService.getAllParcels());
    }

    @PostMapping("/moveParcel")
    public ApiResponse<?> moveParcel(@Valid @RequestBody MoveParcelRequest request) {
        return ApiResponse.success("Parcel moved successfully", parcelService.moveParcel(request));
    }

    @GetMapping("/parcels/{id}")
    public ApiResponse<?> getParcelById(@PathVariable Long id) {
        return ApiResponse.success("Parcel by ID endpoint", parcelService.findById(id));
    }

    @PutMapping("/parcels/update")
    public ApiResponse<?> updateParcel(@Valid @RequestBody UpdateParcelRequest request) {
        return ApiResponse.success("Parcel info updated successfully", parcelService.updateParcel(request));
    }


}
