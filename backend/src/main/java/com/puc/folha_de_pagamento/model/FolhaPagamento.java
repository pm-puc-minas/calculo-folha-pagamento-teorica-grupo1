package com.puc.folha_de_pagamento.model;

import java.time.LocalDate;

import com.puc.folha_de_pagamento.calculos.adicional.Insalubridade;
import com.puc.folha_de_pagamento.calculos.adicional.Periculosidade;
import com.puc.folha_de_pagamento.calculos.beneficio.ValeTransporte;
import com.puc.folha_de_pagamento.calculos.desconto.INSS;
import com.puc.folha_de_pagamento.calculos.desconto.IRRF;

public class FolhaPagamento {
    
    private Funcionario funcionario;
    private LocalDate dataReferencia;
    private double salarioBruto;
    private double salarioLiquido;
    
    public FolhaPagamento() {
        this.dataReferencia = LocalDate.now();
    }
    
    public FolhaPagamento(Funcionario funcionario) {
        this();
        this.funcionario = funcionario;
        this.salarioBruto = funcionario.getSalarioBruto();
    }
    
    public void calcularSalarioLiquido() {
        if (funcionario == null) {
            throw new IllegalStateException("Funcionário não pode ser nulo");
        }
        
        double totalAdicionais = 0.0;
        double totalDescontos = 0.0;
        
        if (funcionario.isTemPericulosidade()) {
            Periculosidade periculosidade = new Periculosidade();
            totalAdicionais += periculosidade.calcular(funcionario);
        }
        
        if (funcionario.isTemInsalubridade()) {
            Insalubridade insalubridade = new Insalubridade();
            totalAdicionais += insalubridade.calcular(funcionario);
        }
        
        //Não seria melhor usar composição ou agregação nesas classe nesse ponto? FolhaPagamento.INSS?
        INSS inss = new INSS();
        totalDescontos += inss.calcular(funcionario, salarioBruto);
        
        IRRF irrf = new IRRF();
        totalDescontos += irrf.calcular(funcionario, salarioBruto);
        
        ValeTransporte valeTransporte = new ValeTransporte();
        totalDescontos += valeTransporte.calcular(funcionario);
        
        salarioLiquido = salarioBruto + totalAdicionais - totalDescontos;
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }
    
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        if (funcionario != null) {
            this.salarioBruto = funcionario.getSalarioBruto();
        }
    }
    
    public LocalDate getDataReferencia() {
        return dataReferencia;
    }
    
    public void setDataReferencia(LocalDate dataReferencia) {
        this.dataReferencia = dataReferencia;
    }
    
    public double getSalarioBruto() {
        return salarioBruto;
    }
    
    public void setSalarioBruto(double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }
    
    public double getSalarioLiquido() {
        return salarioLiquido;
    }
    
}
