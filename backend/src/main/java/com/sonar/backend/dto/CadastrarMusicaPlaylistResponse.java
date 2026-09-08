package com.sonar.backend.dto;

public record CadastrarMusicaPlaylistResponse(
        Long idMusica,
        Long idPlaylist,
        Integer posicao
) {}
