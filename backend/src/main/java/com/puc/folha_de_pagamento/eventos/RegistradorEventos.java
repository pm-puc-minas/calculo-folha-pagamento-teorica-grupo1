package com.puc.folha_de_pagamento.eventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.puc.folha_de_pagamento.model.FolhaPagamento;
import com.puc.folha_de_pagamento.model.Funcionario;

public class RegistradorEventos {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static RegistradorEventos instance;
    
    private RegistradorEventos() {
    }
    
    public static synchronized RegistradorEventos getInstance() {
        if (instance == null) {
            instance = new RegistradorEventos();
        }
        return instance;
    }
    
    public void logFuncionarioCadastrado(Funcionario funcionario) {
        String log = String.format("[%s] LOG: Funcionário cadastrado - Nome: %s, CPF: %s, Cargo: %s",
            LocalDateTime.now().format(FORMATTER),
            funcionario.getNome(),
            funcionario.getCpf(),
            funcionario.getCargo());
        System.out.println(log);
    }
    
    public void notificarFolhaGerada(FolhaPagamento folha) {
        Funcionario funcionario = folha.getFuncionario();
        String notificacao = String.format("[%s] NOTIFICAÇÃO: Folha de pagamento gerada para %s (CPF: %s). Salário Líquido: R$ %.2f",
            LocalDateTime.now().format(FORMATTER),
            funcionario.getNome(),
            funcionario.getCpf(),
            folha.getSalarioLiquido());
        System.out.println(notificacao);
    }
}

