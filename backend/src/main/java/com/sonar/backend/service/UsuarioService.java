package com.sonar.backend.service;

import com.sonar.backend.dao.UsuarioDAO;
import com.sonar.backend.dto.*;
import com.sonar.backend.exception.ConflitoException;
import com.sonar.backend.exception.DadosInvalidosException;
import com.sonar.backend.exception.RecursoNaoEncontradoException;
import com.sonar.backend.model.Usuario;
import com.sonar.backend.validator.UsuarioValidador;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioValidador validador;
    private final UsuarioDAO dao;

    public UsuarioService(UsuarioValidador validador, UsuarioDAO dao) {
        this.validador = validador;
        this.dao = dao;
    }

    public CadastroUsuarioResponse cadastrar(CadastroUsuarioRequest request) {
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

        return new CadastroUsuarioResponse(
                usuario.getId(),
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
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

}
