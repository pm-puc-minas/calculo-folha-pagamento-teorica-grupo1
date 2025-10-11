package com.puc.folha_de_pagamento.calculos.beneficio;

import com.puc.folha_de_pagamento.model.Funcionario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RF4 - Calcular Vale Transporte")
class ValeTransporteTest {
    
    @Test
    @DisplayName("Cenário 1: Validar que o desconto de 6% do salário bruto é aplicado quando o valor do vale-transporte recebido é maior ou igual a essa porcentagem")
    void deveDescontar6PorcentoQuandoValorMaior() {
        ValeTransporte valeTransporte = new ValeTransporte();
        Funcionario funcionario = new Funcionario("João Silva", "123.456.789-00", "Analista", 3000.00);
        funcionario.setValorValeTransporte(200.00);
        
        double resultado = valeTransporte.calcular(funcionario);
        
        assertEquals(180.00, resultado);
    }
    
    @Test
    @DisplayName("Cenário 2: Testar se o desconto corresponde ao valor total do vale-transporte recebido quando este é menor que 6% do salário bruto")
    void deveDescontarValorTotalQuandoMenor() {
        ValeTransporte valeTransporte = new ValeTransporte();
        Funcionario funcionario = new Funcionario("Maria Santos", "987.654.321-00", "Analista", 3000.00);
        funcionario.setValorValeTransporte(100.00);
        
        double resultado = valeTransporte.calcular(funcionario);
        
        assertEquals(100.00, resultado);
    }
}