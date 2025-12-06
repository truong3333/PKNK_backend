package com.example.pknk.configuration;

import com.example.pknk.domain.entity.user.Role;
import com.example.pknk.domain.entity.user.User;
import com.example.pknk.domain.entity.user.UserDetail;
import com.example.pknk.repository.user.RoleRepository;
import com.example.pknk.repository.user.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {

        return args -> {
            Role role = Role.builder()
                    .name("ADMIN")
                    .description("role admin")
                    .build();

            if(roleRepository.findById("ADMIN").isEmpty()){
                roleRepository.save(role);
            }

            if (userRepository.findByUsername("admin").isEmpty()) {

                Set<Role> roles = new HashSet<>();
                roles.add(role);

                UserDetail userDetail = UserDetail.builder().build();

                User user = User.builder()
                        .roles(roles)
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .userDetail(userDetail)
                        .build();

                userDetail.setUser(user);

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
        };
    }
}
