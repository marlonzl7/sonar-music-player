package com.sonar.backend.dto;

import java.util.List;

public record ErroResponse(
    Integer status,
    String mensagem,
    List<ErroCampoDTO> erros
) {}
