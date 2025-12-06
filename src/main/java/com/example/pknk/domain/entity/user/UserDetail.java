package com.example.pknk.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String fullName;
    String email;
    String phone;
    String address;
    String gender;
    LocalDate dob;
    LocalDate createAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

}
