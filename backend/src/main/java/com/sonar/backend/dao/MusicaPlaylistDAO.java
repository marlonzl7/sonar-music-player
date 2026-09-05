package com.sonar.backend.dao;

import com.sonar.backend.model.Musica;
import com.sonar.backend.model.MusicaPlaylist;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MusicaPlaylistDAO {

    private final JdbcTemplate jdbcTemplate;

    public MusicaPlaylistDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existePorIdMusicaEIdPlaylist(Long idMusica, Long idPlaylist) {
        String sql = """
                    SELECT COUNT(*)
                    FROM musica_playlist
                    WHERE
                        id_playlist = ? AND
                        id_musica = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idPlaylist,
                idMusica
        );

        return quantidade != null && quantidade > 0;
    }

    public void salvar(MusicaPlaylist entradaMusicaPlaylist) {
        String sql = """
                    INSERT INTO musica_playlist (id_musica, id_playlist, posicao)
                    VALUES (?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                entradaMusicaPlaylist.getIdMusica(),
                entradaMusicaPlaylist.getIdPlaylist(),
                entradaMusicaPlaylist.getPosicao()
        );
    }

    public void atualizarPosicaoAtual(MusicaPlaylist musicaPlaylist) {
        String sql = """
                    UPDATE musica_playlist
                    SET posicao = ?
                    WHERE
                        id_playlist = ? AND
                        id_musica = ?
                """;

        jdbcTemplate.update(
                sql,
                musicaPlaylist.getPosicao(),
                musicaPlaylist.getIdPlaylist(),
                musicaPlaylist.getIdMusica()
        );
    }

    public void atualizarPosicoesParaMenor(Long idPlaylist, Integer posicaoAtual, Integer posicaoAlvo) {
        String sql = """
                    UPDATE musica_playlist
                    SET posicao = posicao - 1
                    WHERE
                        id_playlist = ? AND
                        posicao != ? AND
                        posicao BETWEEN ? AND ?
                """;

        jdbcTemplate.update(
                sql,
                idPlaylist,
                posicaoAtual,
                posicaoAtual,
                posicaoAlvo
        );
    }

    public void atualizarPosicoesParaMaior(Long idPlaylist, Integer posicaoAtual, Integer posicaoAlvo) {
        String sql = """
                    UPDATE musica_playlist
                    SET posicao = posicao + 1
                    WHERE
                        id_playlist = ? AND
                        posicao != ? AND
                        posicao BETWEEN ? AND ?
                """;

        jdbcTemplate.update(
                sql,
                idPlaylist,
                posicaoAtual,
                posicaoAlvo,
                posicaoAtual
        );
    }

    public boolean existePorIdMusicaEIdPlaylistEIdUsuario(Long idMusica, Long idPlaylist, Long idUsuario) {
        String sql = """
                    SELECT COUNT(*)
                    FROM musica_playlist mp
                    JOIN musica m ON mp.id_musica = m.id_musica
                    WHERE
                        mp.id_musica = ? AND
                        mp.id_playlist = ? AND
                        m.id_usuario = ?
                """;

        Integer quantidade = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                idMusica,
                idPlaylist,
                idUsuario
        );

        return quantidade != null && quantidade > 0;
    }

    public List<Musica> listarMusicasPorIdPlaylist(Long idPlaylist) {
        String sql = """
                    SELECT *
                    FROM musica_playlist mp
                    JOIN musica m
                        ON m.id_musica = mp.id_musica
                    WHERE mp.id_playlist = ?
                    ORDER BY mp.posicao
                """;

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(Musica.class),
                idPlaylist
        );
    }

    public Integer obterMaiorPosicao(Long idPlaylist) {
        String sql = """
                    SELECT MAX(posicao)
                    FROM musica_playlist
                    WHERE id_playlist = ?
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    idPlaylist
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    public Integer obterPosicaoPorIdMusicaEIdPlaylist(Long idMusica, Long idPlaylist) {
        String sql = """
                    SELECT posicao
                    FROM musica_playlist mp
                    WHERE
                        id_musica = ? AND
                        id_playlist = ?
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    Integer.class,
                    idMusica,
                    idPlaylist
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void excluir(Long idMusica, Long idPlaylist) {
        String sql = """
                    DELETE FROM musica_playlist
                    WHERE
                        id_musica = ? AND
                        id_playlist = ?
                """;

        jdbcTemplate.update(
                sql,
                idMusica,
                idPlaylist
        );
    }

    public void atualizarPosicoesAposExcluir(Long idPlaylist, Integer posicaoExcluida) {
        String sql = """
                    UPDATE musica_playlist
                    SET
                        posicao = posicao - 1
                    WHERE
                        id_playlist = ? AND
                        posicao > ?
                """;

        jdbcTemplate.update(
                sql,
                idPlaylist,
                posicaoExcluida
        );
    }

    public MusicaPlaylist obterPorIdMusicaEIdPlaylistEIdUsuario(Long idMusica, Long idPlaylist, Long idUsuario) {
        String sql = """
                    SELECT
                        mp.id_musica AS idMusica,
                        mp.id_playlist AS idPlaylist,
                        mp.posicao
                    FROM musica_playlist mp
                    JOIN musica m
                        ON mp.id_musica = m.id_musica
                    WHERE
                        mp.id_playlist = ? AND
                        mp.id_musica = ? AND
                        m.id_usuario = ?
                """;

        try {
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(MusicaPlaylist.class),
                    idPlaylist,
                    idMusica,
                    idUsuario
            );
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

}
