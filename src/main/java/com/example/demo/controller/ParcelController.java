package com.example.demo.controller;

import com.example.demo.entity.Parcel;
import com.example.demo.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParcelController {

    private final ParcelService parcelService;

    @GetMapping("/parcels")
    public List<Parcel> getParcels() {
        return parcelService.getAllParcels();
    }

}
