package com.totvs.gestao_contas_service.infrastructure.rest;

import com.totvs.gestao_contas_service.application.dto.usuario.*;
import com.totvs.gestao_contas_service.application.usecase.usuario.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final CriarUsuarioUseCase criarUsuario;
    private final BuscarUsuarioUseCase buscarUsuario;
    private final ListarUsuariosUseCase listarUsuarios;
    private final AtualizarUsuarioUseCase atualizarUsuario;
    private final AtualizarSenhaUseCase atualizarSenha;
    private final DeletarUsuarioUseCase deletarUsuario;
    private final AutenticarUsuarioUseCase autenticarUsuario;

    public AuthController(CriarUsuarioUseCase criarUsuario, BuscarUsuarioUseCase buscarUsuario,
                          ListarUsuariosUseCase listarUsuarios, AtualizarUsuarioUseCase atualizarUsuario,
                          AtualizarSenhaUseCase atualizarSenha, DeletarUsuarioUseCase deletarUsuario,
                          AutenticarUsuarioUseCase autenticarUsuario) {
        this.criarUsuario = criarUsuario;
        this.buscarUsuario = buscarUsuario;
        this.listarUsuarios = listarUsuarios;
        this.atualizarUsuario = atualizarUsuario;
        this.atualizarSenha = atualizarSenha;
        this.deletarUsuario = deletarUsuario;
        this.autenticarUsuario = autenticarUsuario;
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody CriarUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarUsuario.executar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(autenticarUsuario.executar(request));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(listarUsuarios.executar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(buscarUsuario.executar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable UUID id,
                                                     @Valid @RequestBody AtualizarUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(atualizarUsuario.executar(id, request));
    }

    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> atualizarSenha(@PathVariable UUID id,
                                               @Valid @RequestBody AtualizarSenhaRequest request) {
        atualizarSenha.executar(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarUsuario.executar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
