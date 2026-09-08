package com.sonar.backend.validador;

import com.sonar.backend.dto.AtualizarMusicaPlaylistRequest;
import com.sonar.backend.dto.ErroCampoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MusicaPlaylistValidador {

    public List<ErroCampoDTO> validarAtualizacao(Long idPlaylist, Long idMusica, AtualizarMusicaPlaylistRequest request) {
        List<ErroCampoDTO> erros = new ArrayList<>();

        ErroCampoDTO erroIdPlaylist = validarIdNuloOuInvalido(idPlaylist, "idPlaylist");

        if (erroIdPlaylist != null) {
            erros.add(erroIdPlaylist);
        }

        ErroCampoDTO erroIdMusica = validarIdNuloOuInvalido(idMusica, "idMusica");

        if (erroIdMusica != null) {
            erros.add(erroIdMusica);
        }

        ErroCampoDTO erroIdUsuario = validarIdNuloOuInvalido(request.idUsuario(), "idUsuario");

        if (erroIdUsuario != null) {
            erros.add(erroIdUsuario);
        }

        ErroCampoDTO erroPosicao = validarPosicao(request.posicao());

        if (erroPosicao != null) {
            erros.add(erroPosicao);
        }

        return erros;
    }

    public List<ErroCampoDTO> validarExclusao(Long idMusica, Long idPlaylist, Long idUsuario) {
        List<ErroCampoDTO> erros = new ArrayList<>();

        ErroCampoDTO erroIdMusica = validarIdNuloOuInvalido(idMusica, "idMusica");

        if (erroIdMusica != null) {
            erros.add(erroIdMusica);
        }

        ErroCampoDTO erroIdPlaylist = validarIdNuloOuInvalido(idPlaylist, "idPlaylist");

        if (erroIdPlaylist != null) {
            erros.add(erroIdPlaylist);
        }

        ErroCampoDTO erroIdUsuario = validarIdNuloOuInvalido(idUsuario, "idUsuario");

        if (erroIdUsuario != null) {
            erros.add(erroIdUsuario);
        }

        return erros;
    }

    private ErroCampoDTO validarIdNuloOuInvalido(Long id, String campo) {
        if (UtilitarioValidador.campoNulo(id)) return new ErroCampoDTO(campo, "não pode ser nulo");
        if (id <= 0) return new ErroCampoDTO(campo, "deve ser um número positivo");

        return null;
    }

    private ErroCampoDTO validarPosicao(Integer posicao) {
        if (posicao == null || posicao < 1) return new ErroCampoDTO("posição", "Posição não pode nula ou negativa");

        return null;
    }

}
