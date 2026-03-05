package org.example.sports.repository;

import org.example.sports.domain.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {
}
