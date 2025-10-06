package com.example.pknk.domain.entity.user;

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
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String department;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
}
