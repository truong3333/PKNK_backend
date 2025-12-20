package com.example.pknk.repository.nurse;

import com.example.pknk.domain.entity.user.Nurse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NurseRepository extends JpaRepository<Nurse, String> {
    @EntityGraph(attributePaths = {"user.userDetail"})
    Optional<Nurse> findById(String id);
    
    Optional<Nurse> findByUserId(String userId);
}
