package com.example.demo.service.impl;

import com.example.demo.entity.Parcel;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepository;

    @Override
    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }
}
