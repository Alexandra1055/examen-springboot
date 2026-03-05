package org.example.sports.mapper;

import org.example.sports.domain.Athlete;
import org.example.sports.dto.AthleteDto;
import org.example.sports.dto.AthleteRequest;
import org.springframework.stereotype.Component;

@Component
public class AthleteMapper {

    public AthleteDto toDto(Athlete a) {
        if (a == null) return null;
        return new AthleteDto(a.getId(), a.getName(), a.getCountry(), a.getAge(), a.getSex());
    }

    public Athlete toEntity(AthleteRequest req) {
        if (req == null) return null;
        return Athlete.builder()
                .name(req.name())
                .country(req.country())
                .age(req.age())
                .sex(req.sex())
                .build();
    }

    public void updateEntity(Athlete a, AthleteRequest req) {
        a.setName(req.name());
        a.setCountry(req.country());
        a.setAge(req.age());
        a.setSex(req.sex());
    }
}
