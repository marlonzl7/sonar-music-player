package com.sonar.backend.exception;

import com.sonar.backend.dto.ErroCampoDTO;
import org.springframework.http.HttpStatus;
import java.util.List;

public class ConflitoException extends ExcecaoDeNegocio {
    public ConflitoException(String mensagem, String campo) {
       super(HttpStatus.CONFLICT, mensagem, List.of(new ErroCampoDTO(campo, "já está em uso.")));
    }
}
