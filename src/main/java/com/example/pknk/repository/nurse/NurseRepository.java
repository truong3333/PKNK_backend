package com.example.pknk.repository.nurse;

import com.example.pknk.domain.entity.user.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, String> {
}
