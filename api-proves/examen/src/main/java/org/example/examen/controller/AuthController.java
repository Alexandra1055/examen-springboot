package org.example.examen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.examen.dto.LoginRequest;
import org.example.examen.dto.LoginResponse;
import org.example.examen.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request.email(), request.password());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}
