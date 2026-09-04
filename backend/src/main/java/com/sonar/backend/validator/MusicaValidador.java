package com.sonar.backend.validator;

import com.sonar.backend.dao.AlbumDAO;
import com.sonar.backend.dao.ArtistaDAO;
import com.sonar.backend.dao.GeneroDAO;
import com.sonar.backend.dto.CadastrarMusicaRequest;
import com.sonar.backend.dto.ErroCampoDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MusicaValidador {

    public List<ErroCampoDTO> validarCadastro(CadastrarMusicaRequest request) {
        List<ErroCampoDTO> erros = new ArrayList<>();

        ErroCampoDTO erroIdUsuario = validarIdNuloOuInvalido(request.idUsuario(), "idUsuario");

        if (erroIdUsuario != null) {
            erros.add(erroIdUsuario);
        }

        ErroCampoDTO erroIdArtista = validarIdNuloOuInvalido(request.idArtista(), "idArtista");

        if (erroIdArtista != null) {
            erros.add(erroIdArtista);
        }

        ErroCampoDTO erroIdGenero = validarIdNuloOuInvalido(request.idGenero(), "idGenero");

        if (erroIdGenero != null) {
            erros.add(erroIdGenero);
        }

        ErroCampoDTO erroTitulo = validarString(request.titulo(), "título");

        if (erroTitulo != null) {
            erros.add(erroTitulo);
        }

        ErroCampoDTO erroCaminhoAudio = validarString(request.caminhoAudio(), "caminho áudio");

        if (erroCaminhoAudio != null) {
            erros.add(erroCaminhoAudio);
        }

        if (request.idAlbum() != null && request.idAlbum() <= 0) {
            erros.add(new ErroCampoDTO("idAlbum", "deve ser um número positivo"));
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
