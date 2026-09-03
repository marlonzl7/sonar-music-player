package com.sonar.backend.service;

import com.sonar.backend.dao.AlbumDAO;
import com.sonar.backend.dao.ArtistaDAO;
import com.sonar.backend.dao.GeneroDAO;
import com.sonar.backend.dao.MusicaDAO;
import com.sonar.backend.dto.*;
import com.sonar.backend.exception.ConflitoException;
import com.sonar.backend.exception.DadosInvalidosException;
import com.sonar.backend.exception.RecursoNaoEncontradoException;
import com.sonar.backend.model.Album;
import com.sonar.backend.model.Artista;
import com.sonar.backend.model.Genero;
import com.sonar.backend.model.Musica;
import com.sonar.backend.utilitarios.Utilitario;
import com.sonar.backend.validator.MusicaValidador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicaService {

    private final MusicaValidador validador;
    private final MusicaDAO dao;
    private final ArtistaDAO artistaDAO;
    private final AlbumDAO albumDAO;
    private final GeneroDAO generoDAO;


    public MusicaService(MusicaValidador validador, MusicaDAO dao, ArtistaDAO artistaDAO, AlbumDAO albumDAO, GeneroDAO generoDAO) {
        this.validador = validador;
        this.dao = dao;
        this.artistaDAO = artistaDAO;
        this.albumDAO = albumDAO;
        this.generoDAO = generoDAO;
    }

    public List<ObterMusicaResponse> listar(
            Long idUsuario,
            String titulo,
            Long idArtista,
            Long idAlbum,
            Long idGenero,
            String ordenarPor,
            String direcao
    ) {
        if (idUsuario == null) {
            throw new DadosInvalidosException("", List.of());
        }

        if (idArtista != null) {
            Artista artista = Utilitario.obterOuFalhar(artistaDAO.obterPorId(idArtista), "Artista");
        }

        if (idAlbum != null) {
            Album album = Utilitario.obterOuFalhar(albumDAO.obterPorId(idAlbum), "Album");
        }

        if (idGenero != null) {
            Genero genero = Utilitario.obterOuFalhar(generoDAO.obterPorId(idGenero), "Genero");
        }

        List<Musica> musicas = dao.buscar(
                idUsuario,
                titulo,
                idArtista,
                idAlbum,
                idGenero,
                ordenarPor,
                direcao
        );

        return musicas.stream()
                .map(musica -> new ObterMusicaResponse(
                        musica.getIdMusica(),
                        musica.getTitulo(),
                        musica.getDuracao(),
                        musica.getCaminhoAudio(),
                        musica.getCriadoEm(),
                        albumDAO.obterPorId(musica.getIdAlbum()),
                        artistaDAO.obterPorId(musica.getIdArtista()),
                        generoDAO.obterPorId(musica.getIdGenero())
                ))
                .toList();
    }

    public ObterMusicaResponse obterMusicaPorIdEIdUsuario(Long idMusica, Long idUsuario) {
        Musica musica = dao.obterPorIdEIdUsuario(idMusica, idUsuario);

        if (musica == null) {
            throw new RecursoNaoEncontradoException("Música não encontrada");
        }

        Artista artista = Utilitario.obterOuFalhar(artistaDAO.obterPorId(musica.getIdArtista()), "Artista");
        Genero genero = Utilitario.obterOuFalhar(generoDAO.obterPorId(musica.getIdGenero()), "Genero");
        Album album = null;

        if (musica.getIdAlbum() != null) {
            album = albumDAO.obterPorId(musica.getIdAlbum());
        }

        return new ObterMusicaResponse(
                musica.getIdMusica(),
                musica.getTitulo(),
                musica.getDuracao(),
                musica.getCaminhoAudio(),
                musica.getCriadoEm(),
                album,
                artista,
                genero
        );
    }

    public CadastrarMusicaResponse cadastrar(CadastrarMusicaRequest request) {
        List<ErroCampoDTO> errosValidacao = validador.validarCadastro(request);

        if (!errosValidacao.isEmpty()) {
            throw new DadosInvalidosException("Formulário de cadastro contém erros", errosValidacao);
        }

        if (dao.existePorTituloEArtista(request.titulo(), request.idArtista())) {
            throw new ConflitoException("Música já existe no catálogo", "Música");
        }

        Album album = null;
        if (request.idAlbum() != null) {
            album = Utilitario.obterOuFalhar(albumDAO.obterPorId(request.idAlbum()), "Album");
        }

        Musica musica = new Musica();
        musica.setIdUsuario(request.idUsuario());
        musica.setIdAlbum(album != null ? album.getIdAlbum() : null);
        musica.setIdArtista(album != null ? album.getIdArtista() : request.idArtista());
        musica.setIdGenero(request.idGenero());
        musica.setTitulo(request.titulo());
        musica.setDuracao(request.duracao());
        musica.setCaminhoAudio(request.caminhoAudio());

        Musica musicaSalva = dao.salvar(musica);

        Artista artista = artistaDAO.obterPorId(musica.getIdArtista());
        Genero genero = generoDAO.obterPorId(musica.getIdGenero());

        return new CadastrarMusicaResponse(
                musica.getIdMusica(),
                musicaSalva.getTitulo(),
                musicaSalva.getDuracao(),
                musicaSalva.getCaminhoAudio(),
                musicaSalva.getCriadoEm(),
                album,
                artista,
                genero
        );
    }

    public void atualizar(Long idMusica, AtualizarMusicaRequest request) {
        if (!dao.existePorIdEIdUsuario(idMusica, request.idUsuario())) {
            throw new RecursoNaoEncontradoException("Música não encontrada");
        }

        Album album = null;
        if (request.idAlbum() != null) {
            album = Utilitario.obterOuFalhar(albumDAO.obterPorId(request.idAlbum()), "Album");
        }

        Musica musica = new Musica();
        musica.setIdUsuario(request.idUsuario());
        musica.setIdAlbum(album != null ? album.getIdAlbum() : null);
        musica.setIdArtista(album != null ? album.getIdArtista() : request.idArtista());
        musica.setIdGenero(request.idGenero());
        musica.setTitulo(request.titulo());
        musica.setDuracao(request.duracao());
        musica.setCaminhoAudio(request.caminhoAudio());

        dao.atualizar(idMusica, musica);
    }

    public void excluir(Long idMusica, Long idUsuario) {
        if (!dao.existePorIdEIdUsuario(idMusica, idUsuario)) {
            throw new RecursoNaoEncontradoException("Música não encontrada");
        }

        dao.excluir(idMusica);
    }

}
