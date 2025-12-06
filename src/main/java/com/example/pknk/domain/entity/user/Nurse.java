package com.example.pknk.domain.entity.user;

import com.example.pknk.domain.entity.clinic.TreatmentPlans;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL)
    List<TreatmentPlans> listTreatmentPlans = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
}
