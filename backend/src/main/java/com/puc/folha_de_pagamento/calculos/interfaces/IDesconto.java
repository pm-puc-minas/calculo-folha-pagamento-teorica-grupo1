package com.puc.folha_de_pagamento.calculos.interfaces;

import com.puc.folha_de_pagamento.model.Funcionario;

public interface IDesconto {
    
    double calcular(Funcionario funcionario, double salarioBase);
    
    boolean isAplicavel(Funcionario funcionario);
    
    String getDescricao();
    
    double getAliquota();
}
