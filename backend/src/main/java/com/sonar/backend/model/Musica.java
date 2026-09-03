package com.sonar.backend.model;

import java.time.LocalDateTime;

public class Musica {

    private Long idMusica;
    private Long idUsuario;
    private Long idGenero;
    private Long idArtista;
    private Long idAlbum;
    private String titulo;
    private Integer duracao;
    private String caminhoAudio;
    private LocalDateTime criadoEm;

    public Musica() {
    }

    public Musica(Long idMusica, Long idUsuario, Long idGenero, Long idArtista, Long idAlbum, String titulo, Integer duracao, String caminhoAudio, LocalDateTime criadoEm) {
        this.idMusica = idMusica;
        this.idUsuario = idUsuario;
        this.idGenero = idGenero;
        this.idArtista = idArtista;
        this.idAlbum = idAlbum;
        this.titulo = titulo;
        this.duracao = duracao;
        this.caminhoAudio = caminhoAudio;
        this.criadoEm = criadoEm;
    }

    public Long getIdMusica() {
        return idMusica;
    }

    public void setIdMusica(Long idMusica) {
        this.idMusica = idMusica;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public Long getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Long idArtista) {
        this.idArtista = idArtista;
    }

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public String getCaminhoAudio() {
        return caminhoAudio;
    }

    public void setCaminhoAudio(String caminhoAudio) {
        this.caminhoAudio = caminhoAudio;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
