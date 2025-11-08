package com.puc.folha_de_pagamento.calculos.desconto;

import com.puc.folha_de_pagamento.calculos.CalculoBase;
import com.puc.folha_de_pagamento.calculos.interfaces.IDesconto;
import com.puc.folha_de_pagamento.model.Funcionario;

public class INSS extends CalculoBase implements IDesconto {
    
    private static final double TETO_INSS = 877.24;
    
    private enum FaixaINSS {
        FAIXA1(1302.00, 0.075),
        FAIXA2(2571.29, 0.09),
        FAIXA3(3856.94, 0.12),
        FAIXA4(7507.49, 0.14);
        
        private final double limite;
        private final double aliquota;
        
        FaixaINSS(double limite, double aliquota) {
            this.limite = limite;
            this.aliquota = aliquota;
        }
        
        public double getLimite() {
            return limite;
        }
        
        public double getAliquota() {
            return aliquota;
        }
    }
    
    @Override
    public double calcular(Funcionario funcionario, double salarioBase) {
        validarFuncionario(funcionario);
        validarValorPositivo(salarioBase, "Salário base");
        
        boolean podeAplicar = isAplicavel(funcionario);
        if (!podeAplicar) {
            return 0.0;
        }
        
        double resultadoFinal = calcularINSS(salarioBase);
        return resultadoFinal;
    }
    
    @Override
    public boolean isAplicavel(Funcionario funcionario) {
        validarFuncionario(funcionario);
        boolean aplicavelATodos = true;
        return aplicavelATodos;
    }
    
    @Override
    public String getDescricao() {
        return "INSS";
    }
    
    @Override
    public double getAliquota() {
        return 0.0;
    }
    
    private double calcularINSS(double salario) {
        double valorCalculado = 0.0;
        
        if (salario <= FaixaINSS.FAIXA1.getLimite()) {
            valorCalculado = salario * FaixaINSS.FAIXA1.getAliquota();
        } else if (salario <= FaixaINSS.FAIXA2.getLimite()) {
            double primeiraParte = FaixaINSS.FAIXA1.getLimite() * FaixaINSS.FAIXA1.getAliquota();
            double segundaParte = (salario - FaixaINSS.FAIXA1.getLimite()) * FaixaINSS.FAIXA2.getAliquota();
            valorCalculado = primeiraParte + segundaParte;
        } else if (salario <= FaixaINSS.FAIXA3.getLimite()) {
            double parte1 = FaixaINSS.FAIXA1.getLimite() * FaixaINSS.FAIXA1.getAliquota();
            double parte2 = (FaixaINSS.FAIXA2.getLimite() - FaixaINSS.FAIXA1.getLimite()) * FaixaINSS.FAIXA2.getAliquota();
            double parte3 = (salario - FaixaINSS.FAIXA2.getLimite()) * FaixaINSS.FAIXA3.getAliquota();
            valorCalculado = parte1 + parte2 + parte3;
        } else if (salario <= FaixaINSS.FAIXA4.getLimite()) {
            double calc1 = FaixaINSS.FAIXA1.getLimite() * FaixaINSS.FAIXA1.getAliquota();
            double calc2 = (FaixaINSS.FAIXA2.getLimite() - FaixaINSS.FAIXA1.getLimite()) * FaixaINSS.FAIXA2.getAliquota();
            double calc3 = (FaixaINSS.FAIXA3.getLimite() - FaixaINSS.FAIXA2.getLimite()) * FaixaINSS.FAIXA3.getAliquota();
            double calc4 = (salario - FaixaINSS.FAIXA3.getLimite()) * FaixaINSS.FAIXA4.getAliquota();
            valorCalculado = calc1 + calc2 + calc3 + calc4;
        } else {
            valorCalculado = TETO_INSS;
        }
        
        return arredondar(valorCalculado);
    }
}
