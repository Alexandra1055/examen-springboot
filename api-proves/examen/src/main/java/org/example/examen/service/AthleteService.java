package org.example.examen.service;

import lombok.RequiredArgsConstructor;
import org.example.examen.domain.Athlete;
import org.example.examen.dto.CreateAthleteDto;
import org.example.examen.error.ResourceNotFoundException;
import org.example.examen.mapper.AthleteMapper;
import org.example.examen.repository.AthleteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final AthleteMapper athleteMapper;

    public List<Athlete> findAll() {
        return athleteRepository.findAll();
    }

    public Athlete findById(Long id) {
        return athleteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Athlete not found with id " + id));
    }

    public Athlete create(CreateAthleteDto request) {
        Athlete athlete = athleteMapper.toEntity(request);
        return athleteRepository.save(athlete);
    }

    public Athlete update(Long id, CreateAthleteDto request) {
        Athlete athlete = findById(id);
        athleteMapper.updateEntity(athlete, request);
        return athleteRepository.save(athlete);
    }

    public void delete(Long id) {
        Athlete athlete = findById(id);
        athleteRepository.delete(athlete);
    }
}
