package com.sonar.backend.controller;

import com.sonar.backend.model.Artista;
import com.sonar.backend.service.ArtistaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/artistas")
public class ArtistaController {

    private final ArtistaService service;

    public ArtistaController(ArtistaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Artista>> listar() {
        List<Artista> response = service.listar();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
