package com.puc.folha_de_pagamento.auth;

import java.util.HashMap;
import java.util.Map;

public class SistemaAutenticacao {
    
    private static SistemaAutenticacao instance;
    private final Map<String, String> usuarios;
    private String usuarioLogado;
    
    private SistemaAutenticacao() {
        this.usuarios = new HashMap<>();
        inicializarUsuariosPadrao();
    }
    
    public static synchronized SistemaAutenticacao getInstance() {
        if (instance == null) {
            instance = new SistemaAutenticacao();
        }
        return instance;
    }
    
    private void inicializarUsuariosPadrao() {
        usuarios.put("admin", "admin123");
        usuarios.put("usuario", "senha123");
    }
    
    public boolean autenticar(String usuario, String senha) {
        if (usuario == null || senha == null) {
            return false;
        }
        
        String senhaArmazenada = usuarios.get(usuario);
        if (senhaArmazenada != null && senhaArmazenada.equals(senha)) {
            this.usuarioLogado = usuario;
            return true;
        }
        
        return false;
    }
    
    public void desautenticar() {
        this.usuarioLogado = null;
    }
    
    public boolean estaAutenticado() {
        return usuarioLogado != null;
    }
    
    public String getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public void cadastrarUsuario(String usuario, String senha) {
        if (usuario != null && senha != null && !usuario.trim().isEmpty()) {
            usuarios.put(usuario, senha);
        }
    }
    
    public boolean usuarioExiste(String usuario) {
        return usuarios.containsKey(usuario);
    }
}

