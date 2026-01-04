package com.example.demo.controller;

import com.example.demo.entity.Parcel;
import com.example.demo.request.MoveParcelRequest;
import com.example.demo.service.ParcelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping("/parcels")
    public List<Parcel> getParcels() {
        return parcelService.getAllParcels();
    }

    @PostMapping("/moveParcel")
    public Map<String, Object> moveParcel(@Valid @RequestBody MoveParcelRequest request) {
        Map<String, Object> response = new java.util.HashMap<>();
        response.put("message", "Parcel moved successfully");
        response.put("status", "success");
        response.put("data", parcelService.moveParcel(request));
        return response;
    }

    @GetMapping("/parcels/{id}")
    public Map<String, Object> getParcelById(@PathVariable Long id) {
        Map<String, Object> info = new java.util.HashMap<>();
        info.put("message", "Parcel by ID endpoint");
        info.put("status", "success");
        info.put("data", parcelService.findById(id)); // Replace null with actual data
        return info;
    }


}
