package org.example.examen.config;

import lombok.RequiredArgsConstructor;
import org.example.examen.domain.*;
import org.example.examen.repository.AthleteRepository;
import org.example.examen.repository.EventRepository;
import org.example.examen.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final AthleteRepository athleteRepository;
    private final EventRepository eventRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner seedDatabase() {
        return args -> {
            userRepository.findByEmail("admin@sports.dev").orElseGet(() -> {
                AppUser admin = AppUser.builder()
                        .email("admin@sports.dev")
                        .passwordHash(passwordEncoder.encode("1234"))
                        .roles("ROLE_ADMIN")
                        .build();
                return userRepository.save(admin);
            });

            if (athleteRepository.count() == 0) {
                athleteRepository.save(Athlete.builder().name("Atleta 1").country("ES").age(23).sex(Sex.MALE).build());
                athleteRepository.save(Athlete.builder().name("Atleta 2").country("FR").age(28).sex(Sex.FEMALE).build());
            }

            if (eventRepository.count() == 0) {
                eventRepository.save(Event.builder().name("100metros Lisos").category(EventCategory.SPRINT).sex(Sex.MALE).finished(false).build());
                eventRepository.save(Event.builder().name("Marató").category(EventCategory.ENDURANCE).sex(Sex.FEMALE).finished(false).build());
            }
        };
    }
}
