package com.example.pknk.domain.entity.clinic;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String publicId;
    String url;
    String type;

    @ManyToOne
    @JoinColumn(name = "examination_id")
    Examination examination;

    @ManyToOne
    @JoinColumn(name = "treatmentPhases_id")
    TreatmentPhases treatmentPhases;

}
