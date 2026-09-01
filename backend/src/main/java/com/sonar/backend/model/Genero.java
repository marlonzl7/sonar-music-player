package com.sonar.backend.model;

public class Genero {

    private Long idGenero;
    private String nome;

    public Genero() {
    }

    public Genero(Long id, String nome) {
        this.idGenero = id;
        this.nome = nome;
    }

    public Long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Long idGenero) {
        this.idGenero = idGenero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
