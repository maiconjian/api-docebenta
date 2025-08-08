package com.doce.benta.controller;


import com.doce.benta.dto.auth.DadosRefreshToken;
import com.doce.benta.dto.auth.DadosToken;
import com.doce.benta.dto.auth.LoginRequest;
import com.doce.benta.model.Usuario;
import com.doce.benta.repository.UsuarioRepository;
import com.doce.benta.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/autenticacao")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest dto){
        var autenticationToken = new UsernamePasswordAuthenticationToken(dto.username(),dto.password());
        var authentication = authenticationManager.authenticate(autenticationToken);
        String token = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        String refreshToken = tokenService.gerarRefreshToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosToken(token,refreshToken));
    }

    @PostMapping("/atualizar-token")
    public ResponseEntity<DadosToken> atualizarDados(@Valid @RequestBody DadosRefreshToken dados){
        var refreshToken = dados.refreshToken();
        UUID idUsuario = UUID.fromString(tokenService.verificarToken(refreshToken));
        var usuario = usuarioRepository.findById(idUsuario).orElseThrow();

        String token = tokenService.gerarToken(usuario);
        String tokenAtualizacao = tokenService.gerarRefreshToken(usuario);
        return ResponseEntity.ok(new DadosToken(token,tokenAtualizacao));

    }
}