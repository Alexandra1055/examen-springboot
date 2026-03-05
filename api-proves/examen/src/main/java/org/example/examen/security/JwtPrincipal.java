package org.example.examen.security;

import java.util.List;

public record JwtPrincipal(String email, List<String> roles) {}
