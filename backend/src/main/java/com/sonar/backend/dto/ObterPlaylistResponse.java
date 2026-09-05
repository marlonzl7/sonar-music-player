package com.sonar.backend.dto;

import com.sonar.backend.model.Musica;

import java.util.List;

public record ObterPlaylistResponse(
        Long idPlaylist,
        String nome,
        List<Musica> musicas
) {}
