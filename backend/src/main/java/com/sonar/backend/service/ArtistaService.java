package com.sonar.backend.service;

import com.sonar.backend.dao.ArtistaDAO;
import com.sonar.backend.model.Artista;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistaService {

    private final ArtistaDAO dao;

    public ArtistaService(ArtistaDAO dao) {
        this.dao = dao;
    }

    public List<Artista> listar() {
        return dao.listar();
    }

}
