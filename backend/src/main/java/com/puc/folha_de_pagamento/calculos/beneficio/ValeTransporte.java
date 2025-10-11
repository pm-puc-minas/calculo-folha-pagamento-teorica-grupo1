package com.puc.folha_de_pagamento.calculos.beneficio;

import com.puc.folha_de_pagamento.calculos.CalculoBase;
import com.puc.folha_de_pagamento.calculos.interfaces.Beneficio;
import com.puc.folha_de_pagamento.model.Funcionario;

public class ValeTransporte extends CalculoBase implements Beneficio {
    
    private static final double PERCENTUAL_DESCONTO = 0.06;
    
    @Override
    public double calcular(Funcionario funcionario) {
        validarFuncionario(funcionario);
        
        boolean podeCalcular = temDireito(funcionario);
        if (!podeCalcular) {
            return 0.0;
        }
        
        double valorSolicitado = funcionario.getValorValeTransporte();
        double salarioBase = funcionario.getSalarioBruto();
        double limiteDesconto = salarioBase * PERCENTUAL_DESCONTO;
        
        double valorFinal = Math.min(valorSolicitado, limiteDesconto);
        return arredondar(valorFinal);
    }
    
    @Override
    public boolean temDireito(Funcionario funcionario) {
        validarFuncionario(funcionario);
        double valorTransporte = funcionario.getValorValeTransporte();
        boolean temValor = valorTransporte > 0;
        return temValor;
    }
    
    @Override
    public String getDescricao() {
        return "Vale Transporte";
    }
    
    @Override
    public boolean isDesconto() {
        return true;
    }
    
    public double getPercentualDesconto() {
        return PERCENTUAL_DESCONTO;
    }
}
