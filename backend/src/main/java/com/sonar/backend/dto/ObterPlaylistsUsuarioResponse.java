package com.sonar.backend.dto;

import java.time.LocalDateTime;

public record ObterPlaylistsUsuarioResponse(
        Long idPlaylist,
        String nome,
        Integer totalMusicas,
        LocalDateTime criadoEm
) {}
