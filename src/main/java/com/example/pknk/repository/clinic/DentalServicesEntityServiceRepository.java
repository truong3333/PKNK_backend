package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.DentalServicesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentalServicesEntityServiceRepository extends JpaRepository<DentalServicesEntity, String> {
}
