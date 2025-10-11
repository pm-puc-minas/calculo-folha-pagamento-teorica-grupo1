package com.puc.folha_de_pagamento.calculos.desconto;

import com.puc.folha_de_pagamento.calculos.CalculoBase;
import com.puc.folha_de_pagamento.calculos.interfaces.Desconto;
import com.puc.folha_de_pagamento.model.Funcionario;

public class INSS extends CalculoBase implements Desconto {
    
    private static final double FAIXA1_LIMITE = 1302.00;
    private static final double FAIXA1_ALIQUOTA = 0.075;
    
    private static final double FAIXA2_LIMITE = 2571.29;
    private static final double FAIXA2_ALIQUOTA = 0.09;
    
    private static final double FAIXA3_LIMITE = 3856.94;
    private static final double FAIXA3_ALIQUOTA = 0.12;
    
    private static final double FAIXA4_LIMITE = 7507.49;
    private static final double FAIXA4_ALIQUOTA = 0.14;
    
    private static final double TETO_INSS = 877.24;
    
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
        
        boolean primeiraFaixa = salario <= FAIXA1_LIMITE;
        if (primeiraFaixa) {
            valorCalculado = salario * FAIXA1_ALIQUOTA;
        } else {
            boolean segundaFaixa = salario <= FAIXA2_LIMITE;
            if (segundaFaixa) {
                double primeiraParte = FAIXA1_LIMITE * FAIXA1_ALIQUOTA;
                double segundaParte = (salario - FAIXA1_LIMITE) * FAIXA2_ALIQUOTA;
                valorCalculado = primeiraParte + segundaParte;
            } else {
                boolean terceiraFaixa = salario <= FAIXA3_LIMITE;
                if (terceiraFaixa) {
                    double parte1 = FAIXA1_LIMITE * FAIXA1_ALIQUOTA;
                    double parte2 = (FAIXA2_LIMITE - FAIXA1_LIMITE) * FAIXA2_ALIQUOTA;
                    double parte3 = (salario - FAIXA2_LIMITE) * FAIXA3_ALIQUOTA;
                    valorCalculado = parte1 + parte2 + parte3;
                } else {
                    boolean quartaFaixa = salario <= FAIXA4_LIMITE;
                    if (quartaFaixa) {
                        double calc1 = FAIXA1_LIMITE * FAIXA1_ALIQUOTA;
                        double calc2 = (FAIXA2_LIMITE - FAIXA1_LIMITE) * FAIXA2_ALIQUOTA;
                        double calc3 = (FAIXA3_LIMITE - FAIXA2_LIMITE) * FAIXA3_ALIQUOTA;
                        double calc4 = (salario - FAIXA3_LIMITE) * FAIXA4_ALIQUOTA;
                        valorCalculado = calc1 + calc2 + calc3 + calc4;
                    } else {
                        valorCalculado = TETO_INSS;
                    }
                }
            }
        }
        
        return arredondar(valorCalculado);
    }
}
