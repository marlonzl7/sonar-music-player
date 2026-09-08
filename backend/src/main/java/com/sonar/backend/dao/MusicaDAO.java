package com.sonar.backend.dao;

import com.sonar.backend.exception.DadosInvalidosException;
import com.sonar.backend.model.Musica;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MusicaDAO {

    private final JdbcTemplate jdbcTemplate;

    public MusicaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Musica> buscar(
            Long idUsuario,
            String titulo,
            Long idArtista,
            Long idAlbum,
            Long idGenero,
            String ordenarPor,
            String direcao
    ) {
        StringBuilder sql = new StringBuilder("""
                    SELECT * FROM musica
                    WHERE id_usuario = ?
                """);

        List<Object> parametros = new ArrayList<>();
        parametros.add(idUsuario);

        if (titulo != null) {
            sql.append(" AND LOWER(titulo) LIKE ?");
            parametros.add("%" + titulo.toLowerCase() + "%");
        }

        if (idArtista != null) {
            sql.append(" AND id_artista = ?");
            parametros.add(idArtista);
        }

        if (idAlbum != null) {
            sql.append(" AND id_album = ?");
            parametros.add(idAlbum);
        }

        if (idGenero != null) {
            sql.append(" AND id_genero = ?");
            parametros.add(idGenero);
        }

        if (ordenarPor != null) {
            Map<String, String> valoresPermitidos = Map.of(
                    "titulo", "titulo",
                    "artista", "id_artista",
                    "album", "id_album",
                    "genero", "id_genero",
                    "dataCadastro", "criado_em",
                    "duracao", "duracao"
            );

            if (!valoresPermitidos.containsKey(ordenarPor)) {
                throw new DadosInvalidosException("Parâmetro 'ordenarPor' inválido", List.of());
            }

            if (direcao != null && !direcao.equalsIgnoreCase("ASC")
                    && !direcao.equalsIgnoreCase("DESC")) {
                throw new DadosInvalidosException("Parâmetro 'direção' inválido", List.of());
            }

            sql.append(" ORDER BY ");
            sql.append(valoresPermitidos.get(ordenarPor));
            sql.append(" ");
            sql.append(direcao != null ? direcao : "ASC");
        } else {
            sql.append(" ORDER BY criado_em DESC");
        }

        return jdbcTemplate.query(
                sql.toString(),
                new BeanPropertyRowMapper<>(Musica.class),
                parametros.toArray()
        );
    }

    public Musica obterPorIdEIdUsuario(Long idMusica, Long idUsuario) {
        String sql = """
                    SELECT *
                    FROM musica
                    WHERE
                        id_musica = ? AND
                        id_usuario = ?
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(Musica.class),
                    idMusica,
                    idUsuario
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Musica salvar(Musica musica) {
        String sql = """
                INSERT INTO musica (id_usuario, id_genero, id_artista, titulo, duracao, caminho_audio, id_album)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(
                    sql,
                    new String[]{"id_musica"}
            );

            preparedStatement.setLong(1, musica.getIdUsuario());
            preparedStatement.setLong(2, musica.getIdGenero());
            preparedStatement.setLong(3, musica.getIdArtista());
            preparedStatement.setString(4, musica.getTitulo());
            preparedStatement.setLong(5, musica.getDuracao());
            preparedStatement.setString(6, musica.getCaminhoAudio());
            preparedStatement.setObject(7, musica.getIdAlbum());

            return preparedStatement;
        }, keyHolder);

        Long idGerado = keyHolder.getKeyAs(Long.class);

        LocalDateTime criadoEm = jdbcTemplate.queryForObject(
                "SELECT criado_em FROM musica WHERE id_musica = ?",
                LocalDateTime.class,
                idGerado
        );

        musica.setIdMusica(idGerado);
        musica.setCriadoEm(criadoEm);

        return musica;
    }

    public boolean existePorTituloEArtista(String titulo, Long idArtista) {
        String sql = """
                    SELECT COUNT(*)
                    FROM musica
                    WHERE
                        titulo = ? AND
                        id_artista = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                titulo,
                idArtista
        );

        return quantidade != null && quantidade > 0;
    }

    public void atualizar(Long idMusica, Musica musica) {
        String sql = """
                UPDATE musica
                SET
                    id_usuario = ?,
                    id_genero = ?,
                    id_artista = ?,
                    id_album = ?,
                    titulo = ?,
                    duracao = ?,
                    caminho_audio = ?
                WHERE id_musica = ?
                """;

        jdbcTemplate.update(
                sql,
                musica.getIdUsuario(),
                musica.getIdGenero(),
                musica.getIdArtista(),
                musica.getIdAlbum(),
                musica.getTitulo(),
                musica.getDuracao(),
                musica.getCaminhoAudio(),
                idMusica
        );
    }

    public void excluir(Long idMusica) {
        String sql = "DELETE FROM musica WHERE id_musica = ?";

        jdbcTemplate.update(
                sql,
                idMusica
        );
    }

    public boolean existePorIdEIdUsuario(Long idMusica, Long idUsuario) {
        String sql = """
                    SELECT COUNT(*)
                    FROM musica
                    WHERE
                        id_musica = ? AND
                        id_usuario = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idMusica,
                idUsuario
        );

        return quantidade != null && quantidade > 0;
    }

}
