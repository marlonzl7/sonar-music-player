package com.sonar.backend.dao;

import com.sonar.backend.model.Genero;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Genero obterPorId(Long idGenero) {
        String sql = "SELECT * FROM genero WHERE id_genero = ?";

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Genero.class),
                    idGenero
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public boolean existePorId(Long idGenero) {
        String sql = "SELECT COUNT(*) FROM genero WHERE id_genero = ?";

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idGenero
        );

        return quantidade != null && quantidade > 0;
    }

}
