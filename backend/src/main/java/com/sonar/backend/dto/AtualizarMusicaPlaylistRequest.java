package com.sonar.backend.dto;

public record AtualizarMusicaPlaylistRequest(
        Long idUsuario,
        Integer posicao
) {}
