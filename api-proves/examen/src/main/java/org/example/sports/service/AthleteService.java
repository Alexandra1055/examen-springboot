package org.example.sports.service;

import lombok.RequiredArgsConstructor;
import org.example.sports.domain.Athlete;
import org.example.sports.dto.AthleteRequest;
import org.example.sports.error.ResourceNotFoundException;
import org.example.sports.mapper.AthleteMapper;
import org.example.sports.repository.AthleteRepository;
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

    public Athlete create(AthleteRequest request) {
        Athlete athlete = athleteMapper.toEntity(request);
        return athleteRepository.save(athlete);
    }

    public Athlete update(Long id, AthleteRequest request) {
        Athlete athlete = findById(id);
        athleteMapper.updateEntity(athlete, request);
        return athleteRepository.save(athlete);
    }

    public void delete(Long id) {
        Athlete athlete = findById(id);
        athleteRepository.delete(athlete);
    }
}
