package com.example.demo.repository;

import com.example.demo.entity.Warehouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @EntityGraph(attributePaths = {})
    List<Warehouse> findAll();
}
