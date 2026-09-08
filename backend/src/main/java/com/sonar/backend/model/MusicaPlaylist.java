package com.sonar.backend.model;

public class MusicaPlaylist {

    private Long idMusica;
    private Long idPlaylist;
    private Integer posicao;

    public MusicaPlaylist() {
    }

    public MusicaPlaylist(Long idMusica, Long idPlaylist, Integer posicao) {
        this.idMusica = idMusica;
        this.idPlaylist = idPlaylist;
        this.posicao = posicao;
    }

    public Long getIdMusica() {
        return idMusica;
    }

    public void setIdMusica(Long idMusica) {
        this.idMusica = idMusica;
    }

    public Long getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(Long idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }
}
