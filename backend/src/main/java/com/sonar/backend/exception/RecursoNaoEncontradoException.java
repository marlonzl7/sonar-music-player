package com.sonar.backend.exception;

import org.springframework.http.HttpStatus;

public class RecursoNaoEncontradoException extends ExcecaoDeNegocio {
    public RecursoNaoEncontradoException(String mensagem) {
        super(HttpStatus.NOT_FOUND, mensagem);
    }
}
