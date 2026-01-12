package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StaffPageResponse implements Serializable {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<StaffResponse> staffData;
}
