package com.sonar.backend.exception;

import com.sonar.backend.dto.ErroCampoDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public abstract class ExcecaoDeNegocio extends RuntimeException {

    private final HttpStatus status;
    private final List<ErroCampoDTO> erros;

    protected ExcecaoDeNegocio(HttpStatus status, String mensagem) {
        this(status, mensagem, List.of());
    }

    protected ExcecaoDeNegocio(HttpStatus status, String mensagem, List<ErroCampoDTO> erros) {
        super(mensagem);
        this.status = status;
        this.erros = erros;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<ErroCampoDTO> getErros() {
        return erros;
    }
}
