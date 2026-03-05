package cat.politecnicllevant.examen.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EnrollAthleteDto {
    private Long athleteId;
    private Long eventId;
}
