package com.sonar.backend.dao;

import com.sonar.backend.model.Artista;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtistaDAO {

    private final JdbcTemplate jdbcTemplate;

    public ArtistaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Artista> listar() {
        String sql = "SELECT id_artista AS idArtista, nome FROM artista";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Artista.class)
        );
    }

    public Artista obterPorId(Long idArtista) {
        String sql = "SELECT * FROM artista WHERE id_artista = ?";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Artista.class),
                    idArtista
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public boolean existePorId(Long idArtista) {
        String sql = "SELECT COUNT(*) FROM artista WHERE id_artista = ?";

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idArtista
        );

        return quantidade != null && quantidade > 0;
    }

}
