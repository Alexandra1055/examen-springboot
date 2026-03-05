package org.example.sports.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    /**
     * Rols en format CSV: "ROLE_ADMIN,ROLE_USER".
     */
    @Column(nullable = false)
    private String roles;

    public List<String> rolesList() {
        if (roles == null || roles.isBlank()) return List.of();
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();
    }
}
