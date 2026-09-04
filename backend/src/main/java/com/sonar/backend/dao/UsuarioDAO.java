package com.sonar.backend.dao;

import com.sonar.backend.model.Usuario;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;

@Repository
public class UsuarioDAO {

    public JdbcTemplate jdbcTemplate;

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Usuario salvar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql,
                    new String[]{"id_usuario"}
            );

            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getEmail());

            return preparedStatement;
        }, keyHolder);

        Long idGerado = keyHolder.getKeyAs(Long.class);

        usuario.setIdUsuario(idGerado);

        return usuario;
    }

    public Boolean existePorEmail(String email) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ?";

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                email
        );

        return quantidade != null && quantidade > 0;
    }

    public Usuario buscarPorEmail(String email) {
        String sql = """
                SELECT
                    id_usuario AS idUsuario,
                    nome,
                    email
                FROM usuario
                WHERE email = ?
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Usuario.class),
                    email
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Boolean existePorId(Long idUsuario) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE id_usuario = ?";

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idUsuario
        );

        return quantidade != null && quantidade > 0;
    }

}
