package com.puc.folha_de_pagamento.calculos.adicional;

import com.puc.folha_de_pagamento.calculos.CalculoBase;
import com.puc.folha_de_pagamento.calculos.interfaces.Adicional;
import com.puc.folha_de_pagamento.model.Funcionario;

public class Periculosidade extends CalculoBase implements Adicional {
    
    private static final double PERCENTUAL_PERICULOSIDADE = 0.30;
    
    @Override
    public double calcular(Funcionario funcionario) {
        validarFuncionario(funcionario);
        
        boolean temDireito = temDireito(funcionario);
        if (!temDireito) {
            return 0.0;
        }
        
        double salarioBruto = funcionario.getSalarioBruto();
        double resultado = salarioBruto * PERCENTUAL_PERICULOSIDADE;
        return arredondar(resultado);
    }
    
    @Override
    public boolean temDireito(Funcionario funcionario) {
        validarFuncionario(funcionario);
        boolean possuiPericulosidade = funcionario.isTemPericulosidade();
        return possuiPericulosidade;
    }
    
    @Override
    public String getDescricao() {
        return "Adicional de Periculosidade";
    }
    
    public double getPercentual() {
        return PERCENTUAL_PERICULOSIDADE;
    }
}
