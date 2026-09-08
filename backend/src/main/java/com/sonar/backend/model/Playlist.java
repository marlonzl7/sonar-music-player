package com.sonar.backend.model;

import java.time.LocalDateTime;

public class Playlist {

    private Long idPlaylist;
    private Long idUsuario;
    private String nome;
    private LocalDateTime criadoEm;

    public Playlist() {
    }

    public Playlist(Long idPlaylist, Long idUsuario, String nome, LocalDateTime criadoEm) {
        this.idPlaylist = idPlaylist;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.criadoEm = criadoEm;
    }

    public Long getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(Long idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }
}
