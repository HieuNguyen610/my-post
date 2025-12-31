package com.example.demo.repository;

import com.example.demo.entity.Parcel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelRepository extends JpaRepository<Parcel, Long> {

    @Override
    @EntityGraph(attributePaths = {"warehouse"})
    List<Parcel> findAll();
}
