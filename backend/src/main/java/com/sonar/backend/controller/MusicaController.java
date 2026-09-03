package com.sonar.backend.controller;

import com.sonar.backend.dto.*;
import com.sonar.backend.service.MusicaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/musicas")
public class MusicaController {

    private final MusicaService service;

    public MusicaController(MusicaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ObterMusicaResponse>> listar(
            @RequestParam(required = true) Long idUsuario,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Long idArtista,
            @RequestParam(required = false) Long idAlbum,
            @RequestParam(required = false) Long idGenero,
            @RequestParam(required = false) String ordenarPor,
            @RequestParam(required = false) String direcao

    ) {
        List<ObterMusicaResponse> response = service.listar(
                idUsuario,
                titulo,
                idArtista,
                idAlbum,
                idGenero,
                ordenarPor,
                direcao
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObterMusicaResponse> obterMusicaPorId(
            @PathVariable Long id,
            @RequestParam Long idUsuario
    ) {
        ObterMusicaResponse response = service.obterMusicaPorIdEIdUsuario(id, idUsuario);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<CadastrarMusicaResponse> cadastrar(@RequestBody CadastrarMusicaRequest request) {
        CadastrarMusicaResponse response = service.cadastrar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Long id,
            @RequestBody AtualizarMusicaRequest request
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
