package com.puc.folha_de_pagamento.eventos;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puc.folha_de_pagamento.model.FolhaPagamento;
import com.puc.folha_de_pagamento.model.Funcionario;

class RegistradorEventosTest {
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }
    
    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
    
    @Test
    void testGetInstance() {
        RegistradorEventos instance1 = RegistradorEventos.getInstance();
        RegistradorEventos instance2 = RegistradorEventos.getInstance();
        
        assertNotNull(instance1);
        assertNotNull(instance2);
        assertSame(instance1, instance2);
    }
    
    @Test
    void testLogFuncionarioCadastrado() {
        Funcionario funcionario = new Funcionario("Pedro Costa", "111.222.333-44", "Gerente", 8000.0);
        
        RegistradorEventos registrador = RegistradorEventos.getInstance();
        registrador.logFuncionarioCadastrado(funcionario);
        
        String output = outContent.toString();
        assertNotNull(output);
        assertTrue(output.contains("LOG: Funcionário cadastrado"));
        assertTrue(output.contains("Pedro Costa"));
        assertTrue(output.contains("111.222.333-44"));
        assertTrue(output.contains("Gerente"));
    }
    
    @Test
    void testNotificarFolhaGerada() {
        Funcionario funcionario = new Funcionario("Ana Lima", "555.666.777-88", "Designer", 3500.0);
        FolhaPagamento folha = new FolhaPagamento(funcionario);
        folha.calcularSalarioLiquido();
        
        RegistradorEventos registrador = RegistradorEventos.getInstance();
        registrador.notificarFolhaGerada(folha);
        
        String output = outContent.toString();
        assertNotNull(output);
        assertTrue(output.contains("NOTIFICAÇÃO: Folha de pagamento gerada"));
        assertTrue(output.contains("Ana Lima"));
        assertTrue(output.contains("555.666.777-88"));
    }
}

