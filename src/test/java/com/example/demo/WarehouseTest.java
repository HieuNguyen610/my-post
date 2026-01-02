package com.example.demo;

import com.example.demo.entity.Warehouse;
import com.example.demo.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class WarehouseTest {

    @Mock
    private  WarehouseService warehouseService;

    @Test
    public void whenGetAll_shouldReturnList() {
        // 1. create mock data
        List<Warehouse> mockBooks = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            mockBooks.add(Warehouse.builder()
                    .id((long) i)
                    .code(100000L + i)
                    .address("Address " + i)
                    .capacity(100)
                    .build());
        }

        // 2. define behavior of Repository
        when(warehouseService.findAll()).thenReturn(mockBooks);

        // 3. call service method
        List<Warehouse> actualWarehouses = warehouseService.findAll();

        // 4. assert the result
        assertThat(actualWarehouses.size()).isEqualTo(mockBooks.size());

        // 4.1 ensure repository is called
        verify(warehouseService).findAll();
    }
}
