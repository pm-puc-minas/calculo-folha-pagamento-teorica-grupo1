package com.puc.folha_de_pagamento.calculos.adicional;

import com.puc.folha_de_pagamento.model.Funcionario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RF2 - Calcular Periculosidade")
class PericulosidadeTest {
    
    @Test
    @DisplayName("Cenário 1: Testar se o valor do adicional é calculado como 30% do salário bruto para um funcionário com direito ao benefício")
    void deveCalcular30PorcentoDoSalarioBruto() {
        Periculosidade periculosidade = new Periculosidade();
        Funcionario funcionario = new Funcionario("João Silva", "123.456.789-00", "Analista", 3000.00);
        funcionario.setTemPericulosidade(true);
        
        double resultado = periculosidade.calcular(funcionario);
        
        assertEquals(900.00, resultado);
    }
    
    @Test
    @DisplayName("Cenário 2: Validar que o valor do adicional é zero para um funcionário que não tem direito")
    void deveRetornarZeroQuandoNaoTemDireito() {
        Periculosidade periculosidade = new Periculosidade();
        Funcionario funcionario = new Funcionario("Maria Santos", "987.654.321-00", "Analista", 3000.00);
        funcionario.setTemPericulosidade(false);
        
        double resultado = periculosidade.calcular(funcionario);
        
        assertEquals(0.0, resultado);
    }
}