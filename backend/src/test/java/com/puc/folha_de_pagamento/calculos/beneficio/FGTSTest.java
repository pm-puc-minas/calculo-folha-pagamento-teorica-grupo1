package com.puc.folha_de_pagamento.calculos.beneficio;

import com.puc.folha_de_pagamento.model.Funcionario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RF7 - Calcular FGTS")
class FGTSTest {
    
    @Test
    @DisplayName("Cenário 1: Testar se o cálculo corresponde a 8% do salário bruto do funcionário")
    void calcula8PorcentoDoSalario() {
        FGTS fgts = new FGTS();
        Funcionario funcionario = new Funcionario("João Silva", "123.456.789-00", "Analista", 3000.00);
        
        double resultado = fgts.calcular(funcionario);
        
        assertEquals(240.00, resultado);
    }
}