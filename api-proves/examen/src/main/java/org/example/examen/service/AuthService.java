package org.example.examen.service;

import lombok.RequiredArgsConstructor;
import org.example.examen.domain.AppUser;
import org.example.examen.repository.UserRepository;
import org.example.examen.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(String email, String password) {
        AppUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credencials incorrectes"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadCredentialsException("Credencials incorrectes");
        }

        return jwtService.generateToken(user);
    }
}
