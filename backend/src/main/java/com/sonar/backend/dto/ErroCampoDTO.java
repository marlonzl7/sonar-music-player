package com.sonar.backend.dto;

public record ErroCampoDTO(
        String campo,
        String mensagem
) {}
