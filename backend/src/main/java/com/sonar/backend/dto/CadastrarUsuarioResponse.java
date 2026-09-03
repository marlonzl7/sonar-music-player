package com.sonar.backend.dto;

public record CadastrarUsuarioResponse(
        Long idUsuario,
        String nome,
        String email
) {}
