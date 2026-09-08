package com.sonar.backend.model;

public class Usuario {

    private Long idUsuario;
    private String nome;
    private String email;

    public Usuario() {
    }

    public Usuario(Long idUsuario, String nome, String email) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
