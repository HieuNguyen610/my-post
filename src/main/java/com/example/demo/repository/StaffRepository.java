package com.example.demo.repository;

import com.example.demo.entity.Staff;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    @EntityGraph(attributePaths = {"role"})
    List<Staff> findAll();
}
