package com.puc.folha_de_pagamento.calculos.adicional;

import com.puc.folha_de_pagamento.calculos.CalculoBase;
import com.puc.folha_de_pagamento.calculos.interfaces.IAdicional;
import com.puc.folha_de_pagamento.model.Funcionario;

public class Insalubridade extends CalculoBase implements IAdicional {
    
    @Override
    public double calcular(Funcionario funcionario) {
        validarFuncionario(funcionario);
        
        boolean elegivel = temDireito(funcionario);
        if (!elegivel) {
            return 0.0;
        }
        
        Funcionario.GrauInsalubridade grau = funcionario.getGrauInsalubridade();
        double percentualGrau = grau.getPercentual();
        double salarioMinimo = SALARIO_MINIMO;
        
        double valorCalculado = salarioMinimo * percentualGrau;
        return arredondar(valorCalculado);
    }
    
    @Override
    public boolean temDireito(Funcionario funcionario) {
        validarFuncionario(funcionario);
        boolean temInsalubridade = funcionario.isTemInsalubridade();
        Funcionario.GrauInsalubridade grau = funcionario.getGrauInsalubridade();
        boolean grauValido = grau != Funcionario.GrauInsalubridade.NENHUM;
        return temInsalubridade && grauValido;
    }
    
    @Override
    public String getDescricao() {
        return "Adicional de Insalubridade";
    }
    
    public double calcularPorGrau(Funcionario.GrauInsalubridade grau) {
        boolean grauInvalido = (grau == null) || (grau == Funcionario.GrauInsalubridade.NENHUM);
        if (grauInvalido) {
            return 0.0;
        }
        
        double percentualDoGrau = grau.getPercentual();
        double valorBase = SALARIO_MINIMO;
        double resultado = valorBase * percentualDoGrau;
        return arredondar(resultado);
    }
}
