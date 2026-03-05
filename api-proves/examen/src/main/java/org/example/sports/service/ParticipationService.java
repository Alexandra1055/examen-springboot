package org.example.sports.service;

import lombok.RequiredArgsConstructor;
import org.example.sports.domain.Athlete;
import org.example.sports.domain.Event;
import org.example.sports.domain.Participation;
import org.example.sports.dto.ParticipationEnrollRequest;
import org.example.sports.error.BusinessRuleException;
import org.example.sports.mapper.ParticipationMapper;
import org.example.sports.repository.ParticipationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final AthleteService athleteService;
    private final EventService eventService;
    private final ParticipationRepository participationRepository;
    private final ParticipationMapper participationMapper;

    public List<Participation> findByEvent(Long eventId) {
        // Validam que l'event existeixi (i així retornam 404 si no existeix)
        eventService.findById(eventId);
        return participationRepository.findByEvent_Id(eventId);
    }

    public Participation enrollWithoutResult(ParticipationEnrollRequest req) {
        Athlete athlete = athleteService.findById(req.athleteId());
        Event event = eventService.findById(req.eventId());

        // a) No es pot inscriure 2 vegades a la mateixa prova
        if (participationRepository.existsByAthlete_IdAndEvent_Id(athlete.getId(), event.getId())) {
            throw new BusinessRuleException("Aquest atleta ja està inscrit a aquesta prova");
        }

        // b) No es permet inscriure si la prova ja està finalitzada
        if (event.isFinished()) {
            throw new BusinessRuleException("No es pot inscriure: la prova ja està finalitzada");
        }

        // c) L'atleta només es pot inscriure a proves del seu sexe
        if (athlete.getSex() != event.getSex()) {
            throw new BusinessRuleException("No es pot inscriure: el sexe de l'atleta no coincideix amb el de la prova");
        }

        Participation p = Participation.builder()
                .athlete(athlete)
                .event(event)
                .result(null)
                .position(null)
                .build();

        return participationRepository.save(p);
    }

    public org.example.sports.dto.ParticipationDto toDto(Participation p) {
        return participationMapper.toDto(p);
    }
}
