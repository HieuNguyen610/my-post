package com.example.demo;

import com.example.demo.entity.Warehouse;
import com.example.demo.repository.WarehouseRepository;
import com.example.demo.service.WarehouseService;
import com.example.demo.service.impl.WarehouseServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarehouseTest {

    private  WarehouseService warehouseService;

    @Mock
    private WarehouseRepository warehouseRepository;

    private static List<Warehouse> mockBooks = new ArrayList<>();


    @BeforeAll
    static void beforeAll() {
        // 1. create mock data
        for(int i = 0; i < 5; i++) {
            mockBooks.add(Warehouse.builder()
                    .id((long) i)
                    .code(100000L + i)
                    .address("Address " + i)
                    .capacity(100)
                    .build());
        }
    }

    @BeforeEach
    void setUp() {
        warehouseService = new WarehouseServiceImpl(warehouseRepository);
    }

    @Test
    void testGetById_shouldReturnWarehouse() {
        // 2. define behavior of Repository
        when(warehouseRepository.findById(1L)).thenReturn(Optional.of(mockBooks.get(0)));

        // 3. call service method
        Warehouse actualWarehouse = warehouseService.findById(1L);

        // 4. assert the result
        assertThat(actualWarehouse.getId()).isEqualTo(mockBooks.get(0).getId());
        assertThat(actualWarehouse.getCode()).isEqualTo(mockBooks.get(0).getCode());
        assertThat(actualWarehouse.getAddress()).isEqualTo(mockBooks.get(0).getAddress());
        assertThat(actualWarehouse.getCapacity()).isEqualTo(mockBooks.get(0).getCapacity());

        // 4.1 ensure repository is called
        verify(warehouseRepository).findById(1L);
    }

    @Test
    void whenGetAll_shouldReturnList() {


        // 2. define behavior of Repository
        when(warehouseRepository.findAll()).thenReturn(mockBooks);

        // 3. call service method
        List<Warehouse> actualWarehouses = warehouseService.findAll();

        // 4. assert the result
        assertThat(actualWarehouses.size()).isEqualTo(mockBooks.size());

        // 4.1 ensure repository is called
        verify(warehouseRepository).findAll();
    }
}
