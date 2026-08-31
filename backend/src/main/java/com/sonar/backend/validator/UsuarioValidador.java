package com.sonar.backend.validator;

import com.sonar.backend.dto.CadastroUsuarioRequest;
import com.sonar.backend.dto.ErroCampoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioValidador {

    public List<ErroCampoDTO> validar(CadastroUsuarioRequest request) {
        List<ErroCampoDTO> erros = new ArrayList<>();

        ErroCampoDTO erroNome = validarNome(request.nome());

        if (erroNome != null) {
            erros.add(erroNome);
        }

        ErroCampoDTO erroEmail = validarEmail(request.email());

        if (erroEmail != null) {
            erros.add(erroEmail);
        }

        return erros;
    }

    private ErroCampoDTO validarNome(String nome) {
        if (nome == null) return new ErroCampoDTO("nome", "não pode ser nulo");
        if (nome.isBlank()) return new ErroCampoDTO("nome", "não pode estar em branco");
        if (nome.length() < 3) return new ErroCampoDTO("nome", "deve conter pelo menos 3 caracteres");

        return null;
    }

    private ErroCampoDTO validarEmail(String email) {
        if (email == null) return new ErroCampoDTO("email", "não pode ser nulo");
        if (email.isBlank()) return new ErroCampoDTO("email", "não pode estar em branco");
        if (!email.contains("@")) return new ErroCampoDTO("email", "deve conter '@'");
        if (!email.contains(".")) return new ErroCampoDTO("email", "deve conter '.' após o '@'");

        return null;
    }

}
