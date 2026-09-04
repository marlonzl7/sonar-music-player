package com.sonar.backend.controller;

import com.sonar.backend.dto.*;
import com.sonar.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CadastrarUsuarioResponse> cadastrar(@RequestBody CadastrarUsuarioRequest request) {
        CadastrarUsuarioResponse response = service.cadastrar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ObterUsuarioPorEmailResponse> login(@RequestBody ObterUsuarioPorEmailRequest request) {
        ObterUsuarioPorEmailResponse response = service.obterUsuarioPorEmail(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/{id}/playlists")
    public ResponseEntity<List<ObterPlaylistsUsuarioResponse>> listarPlaylists(
            @PathVariable Long id
    ) {
        List<ObterPlaylistsUsuarioResponse> response = service.listarPlaylists(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
