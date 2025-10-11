package com.puc.folha_de_pagamento.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RF1 - Calcular Salário Hora")
class JornadaTrabalhoTest {
    
    @Test
    @DisplayName("Cenário 1: Verificar o cálculo do salário por hora para um salário bruto de R$ 2.500,00 com uma jornada mensal de 200 horas")
    void calculaSalarioHora200Horas() {
        JornadaTrabalho jornada = new JornadaTrabalho(200, 8, 22);
        
        double resultado = jornada.calcularSalarioHora(2500.00);
        
        assertEquals(12.50, resultado);
    }
    
    @Test
    @DisplayName("Cenário 2: Testar o cálculo para uma jornada mensal de 220 horas, garantindo a flexibilidade do sistema")
    void calculaSalarioHora220Horas() {
        JornadaTrabalho jornada = new JornadaTrabalho(220, 8, 22);
        
        double resultado = jornada.calcularSalarioHora(2750.00);
        
        assertEquals(12.50, resultado);
    }
}
