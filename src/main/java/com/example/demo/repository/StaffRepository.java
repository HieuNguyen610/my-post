package com.example.demo.repository;

import com.example.demo.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    @NonNull
    @EntityGraph(attributePaths = {"role"})
    List<Staff> findAll();

    @NonNull
    Optional<Staff> findByEmail(@NonNull String email);

    @NonNull
    @EntityGraph(attributePaths = {"role"})
    @Query("SELECT s FROM staffs s WHERE (:keyword IS NULL OR LOWER(s.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.phone) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Staff> searchByKeyword(@Nullable @Param("keyword") String keyword, @NonNull Pageable pageable);
}
