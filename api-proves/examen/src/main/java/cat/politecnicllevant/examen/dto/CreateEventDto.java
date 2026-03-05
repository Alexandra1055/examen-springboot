package cat.politecnicllevant.examen.dto;

import cat.politecnicllevant.examen.domain.EventCategory;
import cat.politecnicllevant.examen.domain.Sex;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateEventDto {
    @NotBlank(message = "name no puede estar en blanco")
    private String name;
    private EventCategory category;
    private Sex sex;
    private Boolean finished;
}
