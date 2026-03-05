package cat.politecnicllevant.examen.dto;

import cat.politecnicllevant.examen.domain.Sex;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CreateAthleteDto {
    @NotBlank(message = "name no puede estar en blanco")
    private String name;
    @NotBlank(message = "country no puede estar en blanco")
    private String country;
    @Min(value = 18, message = "age no puede ser menor de 18")
    private Integer age;
    private Sex sex;
}
