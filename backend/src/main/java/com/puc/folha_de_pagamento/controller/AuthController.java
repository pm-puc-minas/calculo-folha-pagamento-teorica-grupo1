package com.puc.folha_de_pagamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puc.folha_de_pagamento.auth.SistemaAutenticacao;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final SistemaAutenticacao sistemaAutenticacao;
    
    public AuthController() {
        this.sistemaAutenticacao = SistemaAutenticacao.getInstance();
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (request.getUsuario() == null || request.getSenha() == null) {
            return ResponseEntity.badRequest()
                .body("{\"erro\": \"Usuário e senha são obrigatórios\"}");
        }
        
        boolean autenticado = sistemaAutenticacao.autenticar(request.getUsuario(), request.getSenha());
        
        if (autenticado) {
            return ResponseEntity.ok("{\"mensagem\": \"Login realizado com sucesso\", \"usuario\": \"" + 
                sistemaAutenticacao.getUsuarioLogado() + "\"}");
        } else {
            return ResponseEntity.status(401)
                .body("{\"erro\": \"Usuário ou senha inválidos\"}");
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        sistemaAutenticacao.desautenticar();
        return ResponseEntity.ok("{\"mensagem\": \"Logout realizado com sucesso\"}");
    }
    
    @PostMapping("/status")
    public ResponseEntity<?> status() {
        boolean autenticado = sistemaAutenticacao.estaAutenticado();
        if (autenticado) {
            return ResponseEntity.ok("{\"autenticado\": true, \"usuario\": \"" + 
                sistemaAutenticacao.getUsuarioLogado() + "\"}");
        } else {
            return ResponseEntity.ok("{\"autenticado\": false}");
        }
    }
    
    public static class LoginRequest {
        private String usuario;
        private String senha;
        
        public String getUsuario() {
            return usuario;
        }
        
        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }
        
        public String getSenha() {
            return senha;
        }
        
        public void setSenha(String senha) {
            this.senha = senha;
        }
    }
}

