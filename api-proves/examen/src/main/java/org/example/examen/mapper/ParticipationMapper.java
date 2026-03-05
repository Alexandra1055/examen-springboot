package org.example.examen.mapper;

import org.example.examen.domain.Participation;
import org.example.examen.dto.ParticipationDto;
import org.springframework.stereotype.Component;

@Component
public class ParticipationMapper {

    public ParticipationDto toDto(Participation p) {
        if (p == null) return null;
        var a = p.getAthlete();
        var e = p.getEvent();
        return new ParticipationDto(
                p.getId(),
                a.getId(), a.getName(), a.getCountry(), a.getAge(), a.getSex(),
                e.getId(), e.getName(), e.getCategory(), e.getSex(), e.isFinished(),
                p.getResult(),
                p.getPosition()
        );
    }
}
