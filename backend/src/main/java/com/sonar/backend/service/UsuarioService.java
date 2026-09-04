package com.sonar.backend.service;

import com.sonar.backend.dao.PlaylistDAO;
import com.sonar.backend.dao.UsuarioDAO;
import com.sonar.backend.dto.*;
import com.sonar.backend.exception.ConflitoException;
import com.sonar.backend.exception.DadosInvalidosException;
import com.sonar.backend.exception.RecursoNaoEncontradoException;
import com.sonar.backend.model.Playlist;
import com.sonar.backend.model.Usuario;
import com.sonar.backend.validator.UsuarioValidador;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioValidador validador;
    private final UsuarioDAO dao;
    private final PlaylistDAO playlistDAO;

    public UsuarioService(UsuarioValidador validador, UsuarioDAO dao, PlaylistDAO playlistDAO) {
        this.validador = validador;
        this.dao = dao;
        this.playlistDAO = playlistDAO;
    }

    public CadastrarUsuarioResponse cadastrar(CadastrarUsuarioRequest request) {
        List<ErroCampoDTO> errosValidacao = validador.validar(request);

        if (!errosValidacao.isEmpty()) {
            throw new DadosInvalidosException("Formulário de cadastro contém erros", errosValidacao);
        }

        if (dao.existePorEmail(request.email())) {
            throw new ConflitoException("Email já cadastrado", "email");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());

        usuario = dao.salvar(usuario);

        return new CadastrarUsuarioResponse(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    public ObterUsuarioPorEmailResponse obterUsuarioPorEmail(ObterUsuarioPorEmailRequest request) {
        Usuario usuario = dao.buscarPorEmail(request.email());

        if (usuario == null) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado");
        }

        return new ObterUsuarioPorEmailResponse(
                usuario.getIdUsuario(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    public List<ObterPlaylistsUsuarioResponse> listarPlaylists(Long idUsuario) {
        if (!dao.existePorId(idUsuario)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado");
        }

        List<Playlist> playlists = playlistDAO.listarPlaylistsPorIdUsuario(idUsuario);

        return playlists.stream()
                .map(playlist -> new ObterPlaylistsUsuarioResponse(
                        playlist.getIdPlaylist(),
                        playlist.getNome(),
                        0,
                        playlist.getCriadoEm()
                ))
                .toList();
    }

}
