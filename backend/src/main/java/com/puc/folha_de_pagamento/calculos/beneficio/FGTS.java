package com.puc.folha_de_pagamento.calculos.beneficio;

import com.puc.folha_de_pagamento.calculos.CalculoBase;
import com.puc.folha_de_pagamento.calculos.interfaces.Beneficio;
import com.puc.folha_de_pagamento.model.Funcionario;

public class FGTS extends CalculoBase implements Beneficio {
    
    private static final double PERCENTUAL_FGTS = 0.08;
    
    @Override
    public double calcular(Funcionario funcionario) {
        validarFuncionario(funcionario);
        
        boolean podeProcessar = temDireito(funcionario);
        if (!podeProcessar) {
            return 0.0;
        }
        
        double salarioAtual = funcionario.getSalarioBruto();
        double taxaFGTS = PERCENTUAL_FGTS;
        double valorCalculado = salarioAtual * taxaFGTS;
        
        return arredondar(valorCalculado);
    }
    
    @Override
    public boolean temDireito(Funcionario funcionario) {
        validarFuncionario(funcionario);
        boolean sempreElegivel = true;
        return sempreElegivel;
    }
    
    @Override
    public String getDescricao() {
        return "FGTS";
    }
    
    @Override
    public boolean isDesconto() {
        return false;
    }
    
    public double getPercentual() {
        return PERCENTUAL_FGTS;
    }
}
