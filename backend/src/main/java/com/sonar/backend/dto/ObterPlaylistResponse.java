package com.sonar.backend.dto;

import java.util.List;

public record ObterPlaylistResponse(
        Long idPlaylist,
        String nome,
        List<Object> musicas
) {}
