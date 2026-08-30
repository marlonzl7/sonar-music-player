package com.sonar.backend.exception;

import com.sonar.backend.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExcecaoDeNegocio.class)
    public ResponseEntity<ErroResponse> tratarExcecaoDeNegocio(ExcecaoDeNegocio ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErroResponse(ex.getStatus().value(), ex.getMessage(), ex.getErros()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroResponse> tratarJsonInvalido(HttpMessageNotReadableException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroResponse(HttpStatus.BAD_REQUEST.value(), "Corpo da requisição inválido ou malformado", List.of()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarExcecaoGenerica(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErroResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno no servidor", List.of()));
    }

}
