package com.sonar.backend.exception;

import com.sonar.backend.dto.ErroCampoDTO;
import org.springframework.http.HttpStatus;

import java.util.List;

public class DadosInvalidosException extends ExcecaoDeNegocio {
    public DadosInvalidosException(String mensagem, List<ErroCampoDTO> erros) {
        super(HttpStatus.BAD_REQUEST, mensagem, erros);
    }
}
