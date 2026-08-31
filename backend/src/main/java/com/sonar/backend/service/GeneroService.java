package com.sonar.backend.service;

import com.sonar.backend.dao.GeneroDAO;
import com.sonar.backend.model.Genero;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneroService {

    private final GeneroDAO dao;

    public GeneroService(GeneroDAO dao) {
        this.dao = dao;
    }

    public List<Genero> listar() {
        return dao.listar();
    }

}
