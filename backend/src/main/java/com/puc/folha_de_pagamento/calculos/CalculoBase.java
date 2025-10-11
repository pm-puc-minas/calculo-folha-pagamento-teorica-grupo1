package com.puc.folha_de_pagamento.calculos;

import com.puc.folha_de_pagamento.model.Funcionario;

public abstract class CalculoBase {
    
    protected static final double SALARIO_MINIMO = 1380.60;
    
    protected void validarFuncionario(Funcionario funcionario) {
        if (funcionario == null) {
            throw new IllegalArgumentException("Funcionário não pode ser nulo");
        }
    }
    
    protected void validarValorPositivo(double valor, String nomeCampo) {
        if (valor < 0) {
            throw new IllegalArgumentException(nomeCampo + " não pode ser negativo");
        }
    }
    
    protected double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
