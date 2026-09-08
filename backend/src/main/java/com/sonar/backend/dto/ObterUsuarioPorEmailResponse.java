package com.sonar.backend.dto;

public record ObterUsuarioPorEmailResponse(
        Long idUsuario,
        String nome,
        String email
) {}
