package com.example.demo.service.impl;

import com.example.demo.entity.Parcel;
import com.example.demo.entity.ParcelStatus;
import com.example.demo.entity.Warehouse;
import com.example.demo.repository.ParcelRepository;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.request.MoveParcelRequest;
import com.example.demo.request.UpdateParcelRequest;
import com.example.demo.response.MoveParcelResponse;
import com.example.demo.response.ParcelResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParcelServiceImplTest {

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private ParcelServiceImpl parcelService;

    private Parcel parcel;
    private Warehouse newWarehouse;

    @BeforeEach
    void setUp() {
        Warehouse oldWarehouse = Warehouse.builder().id(1L).code(100L).address("Old").capacity(10).build();
        newWarehouse = Warehouse.builder().id(2L).code(200L).address("New").capacity(10).build();

        parcel = new Parcel();
        parcel.setId(1L);
        parcel.setTrackingCode(12345L);
        parcel.setSender("Alice");
        parcel.setReceiver("Bob");
        parcel.setWeight(2.5);
        parcel.setStatus(ParcelStatus.CREATED);
        parcel.setWarehouse(oldWarehouse);
    }

    @Test
    void getAllParcels_returnsAll() {
        List<Parcel> list = Collections.singletonList(parcel);
        when(parcelRepository.findAll()).thenReturn(list);

        List<Parcel> result = parcelService.getAllParcels();

        assertEquals(1, result.size());
        assertSame(parcel, result.get(0));
        verify(parcelRepository, times(1)).findAll();
    }

    @Test
    void moveParcel_success_movesAndSaves() {
        MoveParcelRequest req = new MoveParcelRequest(1L, 2L);

        when(warehouseRepository.findById(newWarehouse.getId())).thenReturn(Optional.of(newWarehouse));
        when(parcelRepository.countByWarehouseId(newWarehouse.getId())).thenReturn(0L);
        when(parcelRepository.findById(parcel.getId())).thenReturn(Optional.of(parcel));
        when(parcelRepository.save(any(Parcel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        MoveParcelResponse resp = parcelService.moveParcel(req);

        assertEquals("Warehouse has enough space", resp.getMessage());
        assertEquals(req.getParcelId(), resp.getParcelId());
        assertEquals(req.getDestinationWarehouseId(), resp.getNewWarehouseId());

        ArgumentCaptor<Parcel> captor = ArgumentCaptor.forClass(Parcel.class);
        verify(parcelRepository, times(1)).save(captor.capture());
        assertEquals(newWarehouse, captor.getValue().getWarehouse());
    }

    @Test
    void moveParcel_destinationFull_doesNotSave() {
        MoveParcelRequest req = new MoveParcelRequest(1L, 2L);

        when(warehouseRepository.findById(newWarehouse.getId())).thenReturn(Optional.of(newWarehouse));
        when(parcelRepository.countByWarehouseId(newWarehouse.getId())).thenReturn(10L); // full

        MoveParcelResponse resp = parcelService.moveParcel(req);

        assertEquals("Warehouse is at full capacity", resp.getMessage());
        verify(parcelRepository, never()).findById(anyLong());
        verify(parcelRepository, never()).save(any());
    }

    @Test
    void moveParcel_warehouseNotFound_doesNotSave() {
        MoveParcelRequest req = new MoveParcelRequest(1L, 99L);

        when(warehouseRepository.findById(99L)).thenReturn(Optional.empty());

        MoveParcelResponse resp = parcelService.moveParcel(req);

        assertEquals("Warehouse not found", resp.getMessage());
        verify(parcelRepository, never()).findById(anyLong());
        verify(parcelRepository, never()).save(any());
    }

    @Test
    void moveParcel_parcelNotFound_throws() {
        MoveParcelRequest req = new MoveParcelRequest(1L, 2L);

        when(warehouseRepository.findById(newWarehouse.getId())).thenReturn(Optional.of(newWarehouse));
        when(parcelRepository.countByWarehouseId(newWarehouse.getId())).thenReturn(0L);
        when(parcelRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parcelService.moveParcel(req));
        assertTrue(ex.getMessage().contains("Parcel not found"));
        verify(parcelRepository, never()).save(any());
    }

    @Test
    void findById_success_returnsResponse() {
        when(parcelRepository.findById(parcel.getId())).thenReturn(Optional.of(parcel));

        ParcelResponse resp = parcelService.findById(parcel.getId());

        assertEquals(parcel.getId(), resp.getId());
        assertEquals(parcel.getTrackingCode(), resp.getTrackingCode());
        assertEquals(parcel.getWeight(), resp.getWeight());
        assertEquals(parcel.getWarehouse(), resp.getWarehouse());
    }

    @Test
    void findById_notFound_throws() {
        when(parcelRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parcelService.findById(1L));
        assertTrue(ex.getMessage().contains("Parcel not found"));
    }

    @Test
    void updateParcel_success_updatesAndReturns() {
        UpdateParcelRequest req = UpdateParcelRequest.builder()
                .id(parcel.getId())
                .trackingCode(999L)
                .sender("Carol")
                .receiver("Dave")
                .weight(5.0)
                .status(ParcelStatus.IN_TRANSIT)
                .build();

        when(parcelRepository.findById(parcel.getId())).thenReturn(Optional.of(parcel));
        when(parcelRepository.save(any(Parcel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ParcelResponse resp = parcelService.updateParcel(req);

        assertEquals(req.getId(), resp.getId());
        assertEquals(req.getTrackingCode(), resp.getTrackingCode());
        assertEquals(req.getSender(), resp.getSender());
        assertEquals(req.getReceiver(), resp.getReceiver());
        assertEquals(req.getWeight(), resp.getWeight());
        assertEquals(req.getStatus(), resp.getStatus());

        verify(parcelRepository, times(1)).save(any(Parcel.class));
    }

    @Test
    void updateParcel_notFound_throws() {
        UpdateParcelRequest req = UpdateParcelRequest.builder().id(999L).build();
        when(parcelRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> parcelService.updateParcel(req));
        assertTrue(ex.getMessage().contains("Parcel not found"));
        verify(parcelRepository, never()).save(any());
    }
}

