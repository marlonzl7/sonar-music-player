package com.sonar.backend.service;

import com.sonar.backend.dao.AlbumDAO;
import com.sonar.backend.exception.DadosInvalidosException;
import com.sonar.backend.model.Album;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumDAO dao;

    public AlbumService(AlbumDAO dao) {
        this.dao = dao;
    }

    public List<Album> listar(Long idArtista) {
        if (idArtista != null) {
            if (!idArtistaValido(idArtista)) {
                throw new DadosInvalidosException("idArtista inválido no parâmetro da requisição", List.of());
            }

            return dao.listarPorIdArtista(idArtista);
        }

        return dao.listar();
    }

    private boolean idArtistaValido(Long idArtista) {
        return idArtista > 0;
    }

}
