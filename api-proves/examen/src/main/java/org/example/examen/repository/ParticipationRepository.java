package org.example.examen.repository;

import org.example.examen.domain.Participation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    boolean existsByAthlete_IdAndEvent_Id(Long athleteId, Long eventId);
    List<Participation> findByEvent_Id(Long eventId);
}
