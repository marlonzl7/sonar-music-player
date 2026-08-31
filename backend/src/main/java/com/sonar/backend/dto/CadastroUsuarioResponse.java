package com.sonar.backend.dto;

public record CadastroUsuarioResponse(
        Long idUsuario,
        String nome,
        String email
) {}
