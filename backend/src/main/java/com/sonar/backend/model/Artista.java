package com.sonar.backend.model;

public class Artista {

    private Long idArtista;
    private String nome;

    public Artista() {
    }

    public Artista(Long id, String nome) {
        this.idArtista = id;
        this.nome = nome;
    }

    public Long getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(Long idArtista) {
        this.idArtista = idArtista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
