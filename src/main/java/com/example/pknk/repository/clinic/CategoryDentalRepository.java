package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.CategoryDental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDentalRepository extends JpaRepository<CategoryDental, String> {
    boolean existsByName(String name);
}
