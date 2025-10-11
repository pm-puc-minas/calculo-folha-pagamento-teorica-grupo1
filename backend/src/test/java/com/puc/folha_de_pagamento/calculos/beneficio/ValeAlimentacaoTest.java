package com.puc.folha_de_pagamento.calculos.beneficio;

import com.puc.folha_de_pagamento.model.Funcionario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RF5 - Calcular Vale Alimentação")
class ValeAlimentacaoTest {
    
    @Test
    @DisplayName("Cenário 1: Verificar se o valor total do vale-alimentação é calculado corretamente com base no valor diário e nos dias úteis do mês")
    void deveCalcularValorTotalCorretamente() {
        ValeAlimentacao valeAlimentacao = new ValeAlimentacao();
        Funcionario funcionario = new Funcionario("João Silva", "123.456.789-00", "Analista", 3000.00);
        funcionario.setValorValeAlimentacao(15.00);
        
        double resultado = valeAlimentacao.calcular(funcionario);
        
        assertEquals(330.00, resultado);
    }
}