package com.totvs.gestao_contas_service.infrastructure.rest;

import com.totvs.gestao_contas_service.application.dto.usuario.*;
import com.totvs.gestao_contas_service.application.usecase.usuario.*;
import com.totvs.gestao_contas_service.infrastructure.rest.handler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Operações de autenticação e gerenciamento de usuários")
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
    @Operation(summary = "Registrar usuário", description = "Cria uma nova conta de usuário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email já cadastrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UsuarioResponse> criar(@Valid @RequestBody CriarUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(criarUsuario.executar(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Realiza login e retorna um token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Email ou senha inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(autenticarUsuario.executar(request));
    }

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna a lista de todos os usuários cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.status(HttpStatus.OK).body(listarUsuarios.executar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os detalhes de um usuário específico")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UsuarioResponse> buscar(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(buscarUsuario.executar(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable UUID id,
                                                      @Valid @RequestBody AtualizarUsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(atualizarUsuario.executar(id, request));
    }

    @PutMapping("/{id}/senha")
    @Operation(summary = "Alterar senha", description = "Altera a senha de um usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou senha atual incorreta",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> atualizarSenha(@PathVariable UUID id,
                                                @Valid @RequestBody AtualizarSenhaRequest request) {
        atualizarSenha.executar(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        deletarUsuario.executar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
