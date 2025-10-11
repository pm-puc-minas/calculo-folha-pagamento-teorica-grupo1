package com.puc.folha_de_pagamento.calculos.desconto;

import com.puc.folha_de_pagamento.model.Funcionario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RF8 - Calcular Desconto de IRRF")
class IRRFTest {
    
    @Test
    @DisplayName("Cenário 1: Testar o cálculo do imposto de renda para um funcionário sem dependentes")
    void deveCalcularSemDependentes() {
        IRRF irrf = new IRRF();
        Funcionario funcionario = new Funcionario("João Silva", "123.456.789-00", "Analista", 3000.00);
        funcionario.setNumeroDependentes(0);
        
        double resultado = irrf.calcular(funcionario, 3000.00);
        
        assertEquals(62.45, resultado);
    }
    
    @Test
    @DisplayName("Cenário 2: Testar o cálculo para um funcionário com dependentes, verificando se a dedução é aplicada corretamente")
    void deveCalcularComDependentes() {
        IRRF irrf = new IRRF();
        Funcionario funcionario = new Funcionario("Maria Santos", "987.654.321-00", "Analista", 3000.00);
        funcionario.setNumeroDependentes(2);
        
        double resultado = irrf.calcular(funcionario, 3000.00);
        
        assertEquals(34.01, resultado);
    }
    
    @Test
    @DisplayName("Cenário 3: Validar se o valor do imposto é zero para um salário isento")
    void deveRetornarZeroParaSalarioIsento() {
        IRRF irrf = new IRRF();
        Funcionario funcionario = new Funcionario("Pedro Costa", "456.789.123-00", "Analista", 2000.00);
        funcionario.setNumeroDependentes(0);
        
        double resultado = irrf.calcular(funcionario, 2000.00);
        
        assertEquals(0.0, resultado);
    }
    
    @Test
    @DisplayName("Cenário 4: Testar se a alíquota e a dedução corretas são aplicadas para um salário na faixa máxima de contribuição")
    void deveCalcularFaixaMaxima() {
        IRRF irrf = new IRRF();
        Funcionario funcionario = new Funcionario("Ana Lima", "789.123.456-00", "Analista", 8000.00);
        funcionario.setNumeroDependentes(0);
        
        double resultado = irrf.calcular(funcionario, 8000.00);
        
        assertEquals(1089.40, resultado);
    }
}