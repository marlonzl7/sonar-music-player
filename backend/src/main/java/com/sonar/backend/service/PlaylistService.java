package com.sonar.backend.service;

import com.sonar.backend.dao.PlaylistDAO;
import com.sonar.backend.dto.*;
import com.sonar.backend.exception.ConflitoException;
import com.sonar.backend.exception.DadosInvalidosException;
import com.sonar.backend.exception.RecursoNaoEncontradoException;
import com.sonar.backend.model.Playlist;
import com.sonar.backend.validator.PlaylistValidador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistDAO dao;
    private final PlaylistValidador validador;

    public PlaylistService(PlaylistDAO dao, PlaylistValidador validador) {
        this.dao = dao;
        this.validador = validador;
    }

    public ObterPlaylistResponse obterPlaylistPorId(Long idPlaylist, Long idUsuario) {
        Playlist playlist = dao.obterPlaylistPorIdEIdUsuario(idPlaylist, idUsuario);

        if (playlist == null) {
            throw new RecursoNaoEncontradoException("Playlist não encontrada");
        }

        return new ObterPlaylistResponse(
                playlist.getIdPlaylist(),
                playlist.getNome(),
                List.of()
        );
    }

    public CadastrarPlaylistResponse cadastrar(CadastrarPlaylistRequest request) {
        List<ErroCampoDTO> errosValidacao = validador.validarCadastro(request);

        if (!errosValidacao.isEmpty()) {
            throw new DadosInvalidosException("Formulário de cadastro contém erros", errosValidacao);
        }

        if (dao.existePorIdUsuarioENome(request.idUsuario(), request.nome())) {
            throw new ConflitoException("Playlist já cadastrada", "Playlist");
        }

        Playlist playlist = new Playlist();
        playlist.setIdUsuario(request.idUsuario());
        playlist.setNome(request.nome());

        Playlist playlistSalva = dao.salvar(playlist);

        return new CadastrarPlaylistResponse(
                playlistSalva.getIdPlaylist(),
                playlistSalva.getIdUsuario(),
                playlistSalva.getNome()
        );
    }

    public void atualizar(Long idPlaylist, AtualizarPlaylistRequest request) {
        List<ErroCampoDTO> errosValidacao = validador.validarAtualizacao(request);

        if (!errosValidacao.isEmpty()) {
            throw new DadosInvalidosException("Formulário de cadastro contém erros", errosValidacao);
        }

        if (!dao.existePorIdEIdUsuario(idPlaylist, request.idUsuario())) {
            throw new RecursoNaoEncontradoException("Playlist não encontrada");
        }

        if (dao.existePorIdUsuarioENome(request.idUsuario(), request.nome())) {
            throw new ConflitoException("Já existe uma playlist com esse nome", "Playlist");
        }

        Playlist playlist = new Playlist();
        playlist.setNome(request.nome());

        dao.atualizar(idPlaylist, playlist);
    }

    public void excluir(Long idPlaylist, Long idUsuario) {
        if (!dao.existePorIdEIdUsuario(idPlaylist, idUsuario)) {
            throw new RecursoNaoEncontradoException("Playlist não encontrada");
        }

        dao.excluir(idPlaylist);
    }

}
