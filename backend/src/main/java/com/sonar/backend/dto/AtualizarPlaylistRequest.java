package com.sonar.backend.dto;

public record AtualizarPlaylistRequest(
        Long idUsuario,
        String nome
) {}
