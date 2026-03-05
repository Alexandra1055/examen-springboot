package org.example.examen.mapper;

import org.example.examen.domain.Athlete;
import org.example.examen.dto.AthleteDto;
import org.example.examen.dto.CreateAthleteDto;
import org.springframework.stereotype.Component;

@Component
public class AthleteMapper {

    public AthleteDto toDto(Athlete a) {
        if (a == null) return null;
        return new AthleteDto(a.getId(), a.getName(), a.getCountry(), a.getAge(), a.getSex());
    }

    public Athlete toEntity(CreateAthleteDto req) {
        if (req == null) return null;
        return Athlete.builder()
                .name(req.name())
                .country(req.country())
                .age(req.age())
                .sex(req.sex())
                .build();
    }

    public void updateEntity(Athlete a, CreateAthleteDto req) {
        a.setName(req.name());
        a.setCountry(req.country());
        a.setAge(req.age());
        a.setSex(req.sex());
    }
}
