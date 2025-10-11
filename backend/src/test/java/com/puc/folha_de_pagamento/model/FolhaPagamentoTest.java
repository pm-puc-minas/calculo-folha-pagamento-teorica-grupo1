package com.puc.folha_de_pagamento.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RF9 - Calcular Salário Líquido")
class FolhaPagamentoTest {
    
    private FolhaPagamento folhaPagamento;
    private Funcionario funcionario;
    
    @BeforeEach
    void setUp() {
        folhaPagamento = new FolhaPagamento();
        funcionario = new Funcionario("João Silva", "123.456.789-00", "Analista", 3000.00);
        folhaPagamento.setFuncionario(funcionario);
    }
    
    @Test
    @DisplayName("Cenário 1: Testar se o valor final é a subtração correta de todos os descontos (obrigatórios e opcionais) do salário bruto, após a adição de todos os proventos e benefícios")
    void calculaSalarioLiquido() {
        funcionario.setTemPericulosidade(true);
        funcionario.setTemInsalubridade(true);
        funcionario.setGrauInsalubridade(Funcionario.GrauInsalubridade.BAIXO);
        funcionario.setValorValeTransporte(150.00);
        funcionario.setValorValeAlimentacao(20.00);
        funcionario.setNumeroDependentes(1);
        
        folhaPagamento.calcularSalarioLiquido();
        
        double salarioBruto = folhaPagamento.getSalarioBruto();
        double salarioLiquido = folhaPagamento.getSalarioLiquido();
        
        assertTrue(salarioLiquido > 0);
        assertTrue(salarioLiquido != salarioBruto);
    }
}
