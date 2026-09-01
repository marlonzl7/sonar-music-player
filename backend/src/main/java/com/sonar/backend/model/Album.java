package com.sonar.backend.model;

public class Album {

    private Long idAlbum;
    private Long idArtista;
    private String titulo;
    private Integer ano;

    public Album() {
    }

    public Album(Long id, Long idArtista, String titulo, Integer ano) {
        this.idAlbum = id;
        this.idArtista = idArtista;
        this.titulo = titulo;
        this.ano = ano;
    }

    public Long getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Long idAlbum) {
        this.idAlbum = idAlbum;
    }

    public Long getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Long idArtista) {
        this.idArtista = idArtista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
