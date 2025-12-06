package com.example.pknk.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String username;
    String password;
    boolean disable;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    UserDetail userDetail;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Patient patient;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Doctor doctor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Nurse nurse;

    @ManyToMany
    Set<Role> roles = new HashSet<>();
}