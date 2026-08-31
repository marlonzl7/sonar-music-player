package com.sonar.backend.controller;

import com.sonar.backend.model.Album;
import com.sonar.backend.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/albuns")
public class AlbumController {

    private final AlbumService service;

    public AlbumController(AlbumService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Album>> listar(
            @RequestParam(required = false) Long idArtista
    ) {
        List<Album> response = service.listar(idArtista);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
