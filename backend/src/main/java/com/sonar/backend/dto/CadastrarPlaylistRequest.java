package com.sonar.backend.dto;

public record CadastrarPlaylistRequest(
        Long idUsuario,
        String nome
) {}
