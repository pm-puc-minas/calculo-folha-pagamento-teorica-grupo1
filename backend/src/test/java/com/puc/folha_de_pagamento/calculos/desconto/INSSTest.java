package com.puc.folha_de_pagamento.calculos.desconto;

import com.puc.folha_de_pagamento.model.Funcionario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RF6 - Calcular Desconto de INSS")
class INSSTest {
    
    @Test
    @DisplayName("Cenário 1: Testar o cálculo progressivo do INSS para um salário que se enquadra na primeira faixa")
    void deveCalcularPrimeiraFaixa() {
        INSS inss = new INSS();
        Funcionario funcionario = new Funcionario("João Silva", "123.456.789-00", "Analista", 1200.00);
        
        double resultado = inss.calcular(funcionario, 1200.00);
        
        assertEquals(90.00, resultado);
    }
    
    @Test
    @DisplayName("Cenário 2: Testar o cálculo do INSS para um salário que atravessa múltiplas faixas")
    void deveCalcularMultiplasFaixas() {
        INSS inss = new INSS();
        Funcionario funcionario = new Funcionario("Maria Santos", "987.654.321-00", "Analista", 3000.00);
        
        double resultado = inss.calcular(funcionario, 3000.00);
        
        assertEquals(263.33, resultado);
    }
    
    @Test
    @DisplayName("Cenário 3: Validar que o cálculo é limitado ao teto máximo da contribuição para salários que excedem esse valor")
    void deveAplicarTetoMaximo() {
        INSS inss = new INSS();
        Funcionario funcionario = new Funcionario("Pedro Costa", "456.789.123-00", "Analista", 10000.00);
        
        double resultado = inss.calcular(funcionario, 10000.00);
        
        assertEquals(877.24, resultado);
    }
}