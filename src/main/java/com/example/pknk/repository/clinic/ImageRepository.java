package com.example.pknk.repository.clinic;

import com.example.pknk.domain.entity.clinic.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    void deleteByPublicId(String publicId);
}
