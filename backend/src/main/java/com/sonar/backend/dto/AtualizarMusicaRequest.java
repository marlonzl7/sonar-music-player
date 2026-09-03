package com.sonar.backend.dto;

public record AtualizarMusicaRequest(
        Long idUsuario,
        Long idArtista,
        Long idAlbum,
        Long idGenero,
        String titulo,
        Integer duracao,
        String caminhoAudio
) {}
