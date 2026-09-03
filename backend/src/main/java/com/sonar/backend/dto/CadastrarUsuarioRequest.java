package com.sonar.backend.dto;

public record CadastrarUsuarioRequest(
        String nome,
        String email
) {}
