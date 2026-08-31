package com.sonar.backend.dao;

import com.sonar.backend.model.Artista;
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
        String sql = "SELECT id_artista AS id, nome FROM artista";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Artista.class)
        );
    }

}
