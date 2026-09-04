package com.sonar.backend.controller;

import com.sonar.backend.dto.*;
import com.sonar.backend.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService service;

    public PlaylistController(PlaylistService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObterPlaylistResponse> obterPlaylistPorId(
            @PathVariable Long id,
            @RequestParam Long idUsuario
    ) {
        ObterPlaylistResponse response = service.obterPlaylistPorId(id, idUsuario);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<CadastrarPlaylistResponse> cadastrar(@RequestBody CadastrarPlaylistRequest request) {
        CadastrarPlaylistResponse response = service.cadastrar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @RequestBody AtualizarPlaylistRequest request
    ) {
        service.atualizar(id, request);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            @RequestParam Long idUsuario
    ) {
        service.excluir(id, idUsuario);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
