package com.puc.folha_de_pagamento.calculos.desconto;

import com.puc.folha_de_pagamento.calculos.CalculoBase;
import com.puc.folha_de_pagamento.calculos.interfaces.IDesconto;
import com.puc.folha_de_pagamento.model.Funcionario;

public class IRRF extends CalculoBase implements IDesconto {
    
    private static final double FAIXA1_LIMITE = 1903.98;
    
    private static final double FAIXA2_LIMITE = 2826.65;
    private static final double FAIXA2_ALIQUOTA = 0.075;
    private static final double FAIXA2_DEDUCAO = 142.80;
    
    private static final double FAIXA3_LIMITE = 3751.05;
    private static final double FAIXA3_ALIQUOTA = 0.15;
    private static final double FAIXA3_DEDUCAO = 354.80;
    
    private static final double FAIXA4_LIMITE = 4664.68;
    private static final double FAIXA4_ALIQUOTA = 0.225;
    private static final double FAIXA4_DEDUCAO = 636.13;
    
    private static final double FAIXA5_ALIQUOTA = 0.275;
    private static final double FAIXA5_DEDUCAO = 869.36;
    
    private static final double DEDUCAO_DEPENDENTE = 189.59;
    
    @Override
    public double calcular(Funcionario funcionario, double salarioBase) {
        validarFuncionario(funcionario);
        validarValorPositivo(salarioBase, "Salário base");
        
        boolean podeCalcular = isAplicavel(funcionario);
        if (!podeCalcular) {
            return 0.0;
        }
        
        double resultadoFinal = calcularIRRF(funcionario, salarioBase);
        return resultadoFinal;
    }
    
    @Override
    public boolean isAplicavel(Funcionario funcionario) {
        validarFuncionario(funcionario);
        boolean sempreAplicavel = true;
        return sempreAplicavel;
    }
    
    @Override
    public String getDescricao() {
        return "IRRF";
    }
    
    @Override
    public double getAliquota() {
        return 0.0;
    }
    
    private double calcularIRRF(Funcionario funcionario, double salarioBase) {
        INSS calculadorINSS = new INSS();
        double descontoINSS = calculadorINSS.calcular(funcionario, salarioBase);
        
        int totalDependentes = funcionario.getNumeroDependentes();
        double deducaoPorDependente = DEDUCAO_DEPENDENTE;
        double totalDeducaoDependentes = totalDependentes * deducaoPorDependente;
        
        double baseParaCalculo = salarioBase - descontoINSS - totalDeducaoDependentes;
        
        boolean baseNegativa = baseParaCalculo <= 0;
        if (baseNegativa) {
            return 0.0;
        }
        
        double impostoCalculado = 0.0;
        
        boolean isentoTributacao = baseParaCalculo <= FAIXA1_LIMITE;
        if (isentoTributacao) {
            impostoCalculado = 0.0;
        } else {
            boolean segundaFaixa = baseParaCalculo <= FAIXA2_LIMITE;
            if (segundaFaixa) {
                double calculoTributavel = baseParaCalculo * FAIXA2_ALIQUOTA;
                impostoCalculado = calculoTributavel - FAIXA2_DEDUCAO;
            } else {
                boolean terceiraFaixa = baseParaCalculo <= FAIXA3_LIMITE;
                if (terceiraFaixa) {
                    double valorTributavel = baseParaCalculo * FAIXA3_ALIQUOTA;
                    impostoCalculado = valorTributavel - FAIXA3_DEDUCAO;
                } else {
                    boolean quartaFaixa = baseParaCalculo <= FAIXA4_LIMITE;
                    if (quartaFaixa) {
                        double calculoImposto = baseParaCalculo * FAIXA4_ALIQUOTA;
                        impostoCalculado = calculoImposto - FAIXA4_DEDUCAO;
                    } else {
                        double tributoFinal = baseParaCalculo * FAIXA5_ALIQUOTA;
                        impostoCalculado = tributoFinal - FAIXA5_DEDUCAO;
                    }
                }
            }
        }
        
        double valorFinal = Math.max(0.0, impostoCalculado);
        return arredondar(valorFinal);
    }
}
