package com.sonar.backend.validator;

import com.sonar.backend.dto.AtualizarPlaylistRequest;
import com.sonar.backend.dto.CadastrarPlaylistRequest;
import com.sonar.backend.dto.ErroCampoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PlaylistValidador {

    public List<ErroCampoDTO> validarCadastro(CadastrarPlaylistRequest request) {
        List<ErroCampoDTO> erros = new ArrayList<>();

        ErroCampoDTO erroIdUsuario = validarIdNuloOuInvalido(request.idUsuario(), "idUsuario");

        if (erroIdUsuario != null) {
            erros.add(erroIdUsuario);
        }

        ErroCampoDTO erroNome = validarString(request.nome(), "nome");

        if (erroNome != null) {
            erros.add(erroNome);
        }

        return erros;
    }

    public List<ErroCampoDTO> validarAtualizacao(AtualizarPlaylistRequest request) {
        List<ErroCampoDTO> erros = new ArrayList<>();

        ErroCampoDTO erroIdUsuario = validarIdNuloOuInvalido(request.idUsuario(), "idUsuario");

        if (erroIdUsuario != null) {
            erros.add(erroIdUsuario);
        }

        ErroCampoDTO erroNome = validarString(request.nome(), "nome");

        if (erroNome != null) {
            erros.add(erroNome);
        }

        return erros;
    }

    private ErroCampoDTO validarIdNuloOuInvalido(Long id, String campo) {
        if (UtilitarioValidador.campoNulo(id)) return new ErroCampoDTO(campo, "não pode ser nulo");
        if (id <= 0) return new ErroCampoDTO(campo, "deve ser um número positivo");

        return null;
    }

    private ErroCampoDTO validarString(String string, String campo) {
        if (UtilitarioValidador.campoNulo(string)) return new ErroCampoDTO(campo, "não pode ser nulo");
        if (string.length() < 3) return new ErroCampoDTO(campo, "deve conter pelo menos 3 caracteres");

        return null;
    }

}
