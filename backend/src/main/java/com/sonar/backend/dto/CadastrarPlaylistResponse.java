package com.sonar.backend.dto;

public record CadastrarPlaylistResponse(
        Long idPlaylist,
        Long idUsuario,
        String nome
) {}
