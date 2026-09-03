package com.sonar.backend.dto;

import com.sonar.backend.model.Album;
import com.sonar.backend.model.Artista;
import com.sonar.backend.model.Genero;

import java.time.LocalDateTime;

public record ObterMusicaResponse(
        Long idMusica,
        String titulo,
        Integer duracao,
        String caminhoAudio,
        LocalDateTime criadoEm,
        Album album,
        Artista artista,
        Genero genero
) {}
