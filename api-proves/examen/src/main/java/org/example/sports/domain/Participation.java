package org.example.sports.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "participations",
        uniqueConstraints = @UniqueConstraint(name = "uk_participation_athlete_event", columnNames = {"athlete_id", "event_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "athlete_id", nullable = false)
    private Athlete athlete;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    // Temps, distancia, etc.
    @Column
    private String result;

    // Posicio classificatoria
    @Column
    private Integer position;
}
