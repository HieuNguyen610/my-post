package com.example.demo.service;

import com.example.demo.entity.Parcel;
import com.example.demo.request.MoveParcelRequest;
import com.example.demo.request.UpdateParcelRequest;
import com.example.demo.response.MoveParcelResponse;
import com.example.demo.response.ParcelResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface ParcelService {
    List<Parcel> getAllParcels();

    MoveParcelResponse moveParcel(MoveParcelRequest request);

    ParcelResponse findById(Long id);

    ParcelResponse updateParcel(@Valid UpdateParcelRequest request);
}
