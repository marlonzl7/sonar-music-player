package com.sonar.backend.controller;

import com.sonar.backend.dto.CadastroUsuarioRequest;
import com.sonar.backend.dto.CadastroUsuarioResponse;
import com.sonar.backend.dto.ObterUsuarioPorEmailRequest;
import com.sonar.backend.dto.ObterUsuarioPorEmailResponse;
import com.sonar.backend.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CadastroUsuarioResponse> cadastrar(@RequestBody CadastroUsuarioRequest request) {
        CadastroUsuarioResponse response = service.cadastrar(request);

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
}
