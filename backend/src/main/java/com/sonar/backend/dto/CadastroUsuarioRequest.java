package com.sonar.backend.dto;

public record CadastroUsuarioRequest(
        String nome,
        String email
) {}
