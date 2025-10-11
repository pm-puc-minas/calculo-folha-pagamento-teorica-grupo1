package com.puc.folha_de_pagamento.calculos.adicional;

import com.puc.folha_de_pagamento.model.Funcionario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RF3 - Calcular Insalubridade")
class InsalubridadeTest {
    
    @Test
    @DisplayName("Cenário 1: Testar o cálculo para o grau de risco baixo (10% do salário mínimo)")
    void deveCalcularGrauBaixo() {
        Insalubridade insalubridade = new Insalubridade();
        Funcionario funcionario = new Funcionario("João Silva", "123.456.789-00", "Analista", 3000.00);
        funcionario.setTemInsalubridade(true);
        funcionario.setGrauInsalubridade(Funcionario.GrauInsalubridade.BAIXO);
        
        double resultado = insalubridade.calcular(funcionario);
        
        assertEquals(138.06, resultado);
    }
    
    @Test
    @DisplayName("Cenário 2: Testar o cálculo para o grau de risco médio (20% do salário mínimo)")
    void deveCalcularGrauMedio() {
        Insalubridade insalubridade = new Insalubridade();
        Funcionario funcionario = new Funcionario("Maria Santos", "987.654.321-00", "Analista", 3000.00);
        funcionario.setTemInsalubridade(true);
        funcionario.setGrauInsalubridade(Funcionario.GrauInsalubridade.MEDIO);
        
        double resultado = insalubridade.calcular(funcionario);
        
        assertEquals(276.12, resultado);
    }
    
    @Test
    @DisplayName("Cenário 3: Testar o cálculo para o grau de risco alto (40% do salário mínimo)")
    void deveCalcularGrauAlto() {
        Insalubridade insalubridade = new Insalubridade();
        Funcionario funcionario = new Funcionario("Pedro Costa", "456.789.123-00", "Analista", 3000.00);
        funcionario.setTemInsalubridade(true);
        funcionario.setGrauInsalubridade(Funcionario.GrauInsalubridade.ALTO);
        
        double resultado = insalubridade.calcular(funcionario);
        
        assertEquals(552.24, resultado);
    }
    
    @Test
    @DisplayName("Cenário 4: Garantir que o valor seja zero para um funcionário sem direito ao adicional")
    void deveRetornarZeroQuandoNaoTemDireito() {
        Insalubridade insalubridade = new Insalubridade();
        Funcionario funcionario = new Funcionario("Ana Lima", "789.123.456-00", "Analista", 3000.00);
        funcionario.setTemInsalubridade(false);
        funcionario.setGrauInsalubridade(Funcionario.GrauInsalubridade.NENHUM);
        
        double resultado = insalubridade.calcular(funcionario);
        
        assertEquals(0.0, resultado);
    }
}