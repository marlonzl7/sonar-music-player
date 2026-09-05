package com.sonar.backend.service;

import com.sonar.backend.dao.MusicaPlaylistDAO;
import com.sonar.backend.dao.MusicaDAO;
import com.sonar.backend.dao.PlaylistDAO;
import com.sonar.backend.dto.*;
import com.sonar.backend.exception.ConflitoException;
import com.sonar.backend.exception.DadosInvalidosException;
import com.sonar.backend.exception.RecursoNaoEncontradoException;
import com.sonar.backend.model.Musica;
import com.sonar.backend.model.MusicaPlaylist;
import com.sonar.backend.model.Playlist;
import com.sonar.backend.validador.MusicaPlaylistValidador;
import com.sonar.backend.validador.PlaylistValidador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistDAO dao;
    private final PlaylistValidador validador;
    private final MusicaDAO musicaDAO;
    private final MusicaPlaylistDAO musicaPlaylistDAO;
    private final MusicaPlaylistValidador musicaPlaylistValidador;

    public PlaylistService(PlaylistDAO dao, PlaylistValidador validador, MusicaDAO musicaDAO, MusicaPlaylistDAO musicaPlaylistDAO, MusicaPlaylistValidador musicaPlaylistValidador) {
        this.dao = dao;
        this.validador = validador;
        this.musicaDAO = musicaDAO;
        this.musicaPlaylistDAO = musicaPlaylistDAO;
        this.musicaPlaylistValidador = musicaPlaylistValidador;
    }

    public ObterPlaylistResponse obterPlaylistPorId(Long idPlaylist, Long idUsuario) {
        Playlist playlist = dao.obterPlaylistPorIdEIdUsuario(idPlaylist, idUsuario);

        if (playlist == null) {
            throw new RecursoNaoEncontradoException("Playlist não encontrada");
        }

        List<Musica> musicas = musicaPlaylistDAO.listarMusicasPorIdPlaylist(idPlaylist);

        return new ObterPlaylistResponse(
                playlist.getIdPlaylist(),
                playlist.getNome(),
                musicas
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

    public CadastrarMusicaPlaylistResponse cadastrarMusicaNaPlaylist(Long idPlaylist, CadastrarMusicaPlaylistRequest request) {
        List<ErroCampoDTO> errosValidacao = validador.validarCadastroMusicaNaPlaylist(request);

        if (!errosValidacao.isEmpty()) {
            throw new DadosInvalidosException("Cadastro contém erros", errosValidacao);
        }

        if (!musicaDAO.existePorIdEIdUsuario(request.idMusica(), request.idUsuario())) {
            throw new RecursoNaoEncontradoException("Música não encontrada");
        }

        if (!dao.existePorIdEIdUsuario(idPlaylist, request.idUsuario())) {
            throw new RecursoNaoEncontradoException("Playlist não encontrada");
        }

        if (musicaPlaylistDAO.existePorIdMusicaEIdPlaylist(request.idMusica(), idPlaylist)) {
            throw new ConflitoException("Esta música já pertence a playlist", null);
        }

        MusicaPlaylist musicaPlaylist = new MusicaPlaylist();
        musicaPlaylist.setIdMusica(request.idMusica());
        musicaPlaylist.setIdPlaylist(idPlaylist);
        musicaPlaylist.setPosicao(gerarPosicaoMusicaPlaylist(idPlaylist));

        musicaPlaylistDAO.salvar(musicaPlaylist);

        return new CadastrarMusicaPlaylistResponse(
                musicaPlaylist.getIdMusica(),
                musicaPlaylist.getIdPlaylist(),
                musicaPlaylist.getPosicao()
        );
    }

    public void atualizarMusicaDaPlaylist(Long idPlaylist, Long idMusica, AtualizarMusicaPlaylistRequest request) {
        List<ErroCampoDTO> errosValidacao = musicaPlaylistValidador.validarAtualizacao(idPlaylist, idMusica, request);

        if (!errosValidacao.isEmpty()) {
            throw new DadosInvalidosException("Atualização contém erros", errosValidacao);
        }

        MusicaPlaylist musicaPlaylistAtual = musicaPlaylistDAO.obterPorIdMusicaEIdPlaylistEIdUsuario(idMusica, idPlaylist, request.idUsuario());

        if (musicaPlaylistAtual == null) {
            throw new ConflitoException("Música não encontrada", null);
        }

        MusicaPlaylist musicaPlaylistNova = new MusicaPlaylist();
        musicaPlaylistNova.setIdPlaylist(idPlaylist);
        musicaPlaylistNova.setIdMusica(idMusica);
        musicaPlaylistNova.setPosicao(request.posicao());

        atualizarPosicao(musicaPlaylistAtual, musicaPlaylistNova);
    }

    public void excluirMusicaDaPlaylist(Long idPlaylist, Long idMusica, Long idUsuario) {
        List<ErroCampoDTO> errosValidacao = musicaPlaylistValidador.validarExclusao(idMusica, idPlaylist, idUsuario);

        if (!errosValidacao.isEmpty()) {
            throw new DadosInvalidosException("Dados inválidos ou faltantes", errosValidacao);
        }

        if (!musicaPlaylistDAO.existePorIdMusicaEIdPlaylistEIdUsuario(idMusica, idPlaylist, idUsuario)) {
            throw new RecursoNaoEncontradoException("Música não encontrada");
        }

        Integer posicao = musicaPlaylistDAO.obterPosicaoPorIdMusicaEIdPlaylist(idMusica, idPlaylist);

        if (posicao == null) {
            throw new RecursoNaoEncontradoException("Música não encontrada");
        }

        musicaPlaylistDAO.excluir(idMusica, idPlaylist);
        musicaPlaylistDAO.atualizarPosicoesAposExcluir(idPlaylist, posicao);
    }

    private Integer gerarPosicaoMusicaPlaylist(Long idPlaylist) {
        Integer maiorPosicaoAtual = musicaPlaylistDAO.obterMaiorPosicao(idPlaylist);
        return (maiorPosicaoAtual == null) ? 1 : maiorPosicaoAtual + 1;
    }

    private void atualizarPosicao(MusicaPlaylist musicaPlaylistAtual, MusicaPlaylist musicaPlaylistNova) {
        Integer posicaoAtual = musicaPlaylistAtual.getPosicao();
        Integer posicaoAlvo = musicaPlaylistNova.getPosicao();

        if (posicaoAtual < posicaoAlvo) {
            musicaPlaylistDAO.atualizarPosicoesParaMenor(musicaPlaylistNova.getIdPlaylist(), posicaoAtual, posicaoAlvo);
        } else {
            musicaPlaylistDAO.atualizarPosicoesParaMaior(musicaPlaylistNova.getIdPlaylist(), posicaoAtual, posicaoAlvo);
        }

        musicaPlaylistDAO.atualizarPosicaoAtual(musicaPlaylistNova);
    }
}
