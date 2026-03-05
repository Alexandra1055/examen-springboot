package cat.politecnicllevant.examen.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginRequest {
    @Email(message = "El email no es correcto")
    private String email;
    @NotBlank(message = "El password no puede estar en blanco")
    @Size(min = 3, message = "El password debe tener al menos 3 caracteres")
    private String password;
}
