package com.example.demo.service.impl;

import com.example.demo.entity.Parcel;
import com.example.demo.entity.Warehouse;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.request.MoveParcelRequest;
import com.example.demo.request.UpdateParcelRequest;
import com.example.demo.response.MoveParcelResponse;
import com.example.demo.response.ParcelResponse;
import com.example.demo.response.WarehouseResponse;
import com.example.demo.service.ParcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParcelServiceImpl implements ParcelService {
    private final ParcelRepository parcelRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }

    @Override
    @Transactional
    public MoveParcelResponse moveParcel(MoveParcelRequest request) {
        /**
         * check if new warehouse exists and has enough space left
         */
        WarehouseResponse warehouseResponse = warehouseRepository.findById(request.getDestinationWarehouseId())
                .map(warehouse -> {
                    long parcelCount = parcelRepository.countByWarehouseId(warehouse.getId());
                    if (parcelCount >= warehouse.getCapacity()) {
                        return new WarehouseResponse(false, "Warehouse is at full capacity");
                    }
                    return new WarehouseResponse(true, "Warehouse has enough space");
                })
                .orElse(new WarehouseResponse(false, "Warehouse not found"));

        if (warehouseResponse.isAvailable()) {
            /**
             * move parcel to new warehouse
             */
            Parcel parcel = parcelRepository.findById(request.getParcelId())
                    .orElseThrow(() -> new RuntimeException("Parcel not found with id: " + request.getParcelId()));
            Warehouse newWarehouse = warehouseRepository.findById(request.getDestinationWarehouseId())
                    .orElseThrow(() -> new RuntimeException("Warehouse not found with id: " + request.getDestinationWarehouseId()));
            parcel.setWarehouse(newWarehouse);
            parcelRepository.save(parcel);
        }

        return MoveParcelResponse.builder()
                .message(warehouseResponse.getMessage())
                .parcelId(request.getParcelId())
                .newWarehouseId(request.getDestinationWarehouseId())
                .build();
    }

    @Override
    @Cacheable(value = "parcels", key = "#id")
    public ParcelResponse findById(Long id) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parcel not found with id: " + id));

        return ParcelResponse.builder()
                .id(parcel.getId())
                .trackingCode(parcel.getTrackingCode())
                .weight(parcel.getWeight())
                .warehouse(parcel.getWarehouse())
                .build();
    }

    @Override
    @Transactional
    public ParcelResponse updateParcel(UpdateParcelRequest request) {
        Parcel parcel = parcelRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Parcel not found with id: " + request.getId()));

        parcel.setTrackingCode(request.getTrackingCode());
        parcel.setSender(request.getSender());
        parcel.setReceiver(request.getReceiver());
        parcel.setWeight(request.getWeight());
        parcel.setStatus(request.getStatus());

        Parcel savedParcel = parcelRepository.save(parcel);

        return ParcelResponse.builder()
                .id(savedParcel.getId())
                .trackingCode(savedParcel.getTrackingCode())
                .sender(savedParcel.getSender())
                .receiver(savedParcel.getReceiver())
                .weight(savedParcel.getWeight())
                .status(savedParcel.getStatus())
                .warehouse(savedParcel.getWarehouse())
                .build();
    }
}
