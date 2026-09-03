package com.sonar.backend.dao;

import com.sonar.backend.model.Album;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlbumDAO {

    private final JdbcTemplate jdbcTemplate;

    public AlbumDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Album> listar() {
        String sql = """
                SELECT
                    id_album AS idUsuario,
                    id_artista AS idArtista,
                    titulo,
                    ano
                FROM album
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Album.class)
        );
    }

    public List<Album> listarPorIdArtista(Long idArtista) {
        String sql = """
                SELECT
                    id_album AS idUsuario,
                    id_artista AS idArtista,
                    titulo,
                    ano
                FROM album
                WHERE id_artista = ?
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Album.class),
                idArtista
        );
    }

    public Album obterPorId(Long idAlbum) {
        String sql = "SELECT * FROM album WHERE id_album = ?";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Album.class),
                    idAlbum
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public boolean existePorId(Long idAlbum) {
        String sql = "SELECT COUNT(*) FROM album WHERE id_album = ?";

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idAlbum
        );

        return quantidade != null && quantidade > 0;
    }

}
