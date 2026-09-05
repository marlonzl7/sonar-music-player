package com.sonar.backend.dto;

public record CadastrarMusicaPlaylistRequest(
        Long idMusica,
        Long idUsuario
) {}
