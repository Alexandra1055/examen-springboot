package org.example.sports.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports.dto.LoginRequest;
import org.example.sports.dto.LoginResponse;
import org.example.sports.service.AuthService;
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
