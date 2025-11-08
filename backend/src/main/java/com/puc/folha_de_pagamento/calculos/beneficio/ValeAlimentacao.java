package com.puc.folha_de_pagamento.calculos.beneficio;

import com.puc.folha_de_pagamento.calculos.CalculoBase;
import com.puc.folha_de_pagamento.calculos.interfaces.IBeneficio;
import com.puc.folha_de_pagamento.model.Funcionario;

public class ValeAlimentacao extends CalculoBase implements IBeneficio {
    
    @Override
    public double calcular(Funcionario funcionario) {
        validarFuncionario(funcionario);
        
        boolean elegivelParaCalculo = temDireito(funcionario);
        if (!elegivelParaCalculo) {
            return 0.0;
        }
        
        double valorPorDia = funcionario.getValorValeAlimentacao();
        int diasUteisMes = 22;
        
        double resultadoCalculo = valorPorDia * diasUteisMes;
        return arredondar(resultadoCalculo);
    }
    
    @Override
    public boolean temDireito(Funcionario funcionario) {
        validarFuncionario(funcionario);
        double valorAlimentacao = funcionario.getValorValeAlimentacao();
        boolean possuiValor = valorAlimentacao > 0;
        return possuiValor;
    }
    
    @Override
    public String getDescricao() {
        return "Vale Alimentação";
    }
    
    @Override
    public boolean isDesconto() {
        return false;
    }
    
}
