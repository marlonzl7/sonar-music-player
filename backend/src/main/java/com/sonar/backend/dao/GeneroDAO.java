package com.sonar.backend.dao;

import com.sonar.backend.model.Genero;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GeneroDAO {

    private final JdbcTemplate jdbcTemplate;

    public GeneroDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Genero> listar() {
        String sql = "SELECT id_genero AS idGenero, nome FROM genero";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Genero.class)
        );
    }

}
