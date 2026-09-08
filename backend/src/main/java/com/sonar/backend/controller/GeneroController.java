package com.sonar.backend.controller;

import com.sonar.backend.model.Genero;
import com.sonar.backend.service.GeneroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService service;

    public GeneroController(GeneroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Genero>> listar() {
        List<Genero> response = service.listar();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
