package com.puc.folha_de_pagamento.calculos.interfaces;

import com.puc.folha_de_pagamento.model.Funcionario;

//Interface deve começar com I: IAdicional
public interface Adicional {
    
    double calcular(Funcionario funcionario);
    
    boolean temDireito(Funcionario funcionario);
    
    String getDescricao();
}
