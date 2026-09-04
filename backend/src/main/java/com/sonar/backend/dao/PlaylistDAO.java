package com.sonar.backend.dao;

import com.sonar.backend.model.Playlist;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class PlaylistDAO {

    private JdbcTemplate jdbcTemplate;

    public PlaylistDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Playlist> listarPlaylistsPorIdUsuario(Long idUsuario) {
        String sql = "SELECT * FROM playlist WHERE id_usuario = ?";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Playlist.class),
                idUsuario
        );
    }

    public Playlist obterPlaylistPorIdEIdUsuario(Long idPlaylist, Long idUsuario) {
        String sql = """
                    SELECT *
                    FROM playlist
                    WHERE
                        id_playlist = ? AND
                        id_usuario = ?
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Playlist.class),
                    idPlaylist,
                    idUsuario
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Playlist salvar(Playlist playlist) {
        String sql = """
                    INSERT INTO playlist (id_usuario, nome)
                    VALUES (?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql,
                    new String[]{"id_playlist"}
            );

            preparedStatement.setLong(1, playlist.getIdUsuario());
            preparedStatement.setString(2, playlist.getNome());

            return preparedStatement;
        }, keyHolder);

        Long idGerado = keyHolder.getKeyAs(Long.class);

        playlist.setIdPlaylist(idGerado);

        return playlist;
    }

    public boolean existePorIdUsuarioENome(Long idUsuario, String nome) {
        String sql = """
                    SELECT COUNT(*)
                    FROM playlist
                    WHERE
                        id_usuario = ? AND
                        nome = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idUsuario,
                nome
        );

        return quantidade != null && quantidade > 0;
    }

    public void atualizar(Long idPlaylist, Playlist playlist) {
        String sql = """
                UPDATE playlist
                SET nome = ?
                WHERE id_playlist = ?
                """;

        jdbcTemplate.update(
                sql,
                playlist.getNome(),
                idPlaylist
        );
    }

    public void excluir(Long idPlaylist) {
        String sql = "DELETE FROM playlist WHERE id_playlist = ?";

        jdbcTemplate.update(
                sql,
                idPlaylist
        );
    }

    public boolean existePorIdEIdUsuario(Long idPlaylist, Long idUsuario) {
        String sql = """
                    SELECT COUNT(*)
                    FROM playlist
                    WHERE
                        id_playlist = ? AND
                        id_usuario = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idPlaylist,
                idUsuario
        );

        return quantidade != null && quantidade > 0;
    }

}
