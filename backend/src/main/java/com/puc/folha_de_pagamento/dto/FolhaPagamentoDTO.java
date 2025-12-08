package com.puc.folha_de_pagamento.dto;

import java.time.LocalDate;
import java.util.Map;

import com.puc.folha_de_pagamento.model.FolhaPagamento;

public class FolhaPagamentoDTO {
    
    private Long id;
    private FuncionarioDTO funcionario;
    private LocalDate dataReferencia;
    private double salarioBruto;
    private double salarioLiquido;
    private double totalAdicionais;
    private double totalDescontos;
    private Map<String, Double> detalhamentoCalculos;
    
    public FolhaPagamentoDTO() {
    }
    
    public FolhaPagamentoDTO(FolhaPagamento folha) {
        if (folha != null) {
            this.id = folha.getId();
            if (folha.getFuncionario() != null) {
                this.funcionario = new FuncionarioDTO(folha.getFuncionario());
            }
            this.dataReferencia = folha.getDataReferencia();
            this.salarioBruto = folha.getSalarioBruto();
            this.salarioLiquido = folha.getSalarioLiquido();
            this.detalhamentoCalculos = folha.getDetalhamentoCalculos();
            
            Double totalAdic = this.detalhamentoCalculos.get("Total Adicionais");
            Double totalDesc = this.detalhamentoCalculos.get("Total Descontos");
            this.totalAdicionais = totalAdic != null ? totalAdic : 0.0;
            this.totalDescontos = totalDesc != null ? Math.abs(totalDesc) : 0.0;
        }
    }
    
    public FuncionarioDTO getFuncionario() {
        return funcionario;
    }
    
    public void setFuncionario(FuncionarioDTO funcionario) {
        this.funcionario = funcionario;
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
    
    public void setSalarioLiquido(double salarioLiquido) {
        this.salarioLiquido = salarioLiquido;
    }
    
    public double getTotalAdicionais() {
        return totalAdicionais;
    }
    
    public void setTotalAdicionais(double totalAdicionais) {
        this.totalAdicionais = totalAdicionais;
    }
    
    public double getTotalDescontos() {
        return totalDescontos;
    }
    
    public void setTotalDescontos(double totalDescontos) {
        this.totalDescontos = totalDescontos;
    }
    
    public Map<String, Double> getDetalhamentoCalculos() {
        return detalhamentoCalculos;
    }
    
    public void setDetalhamentoCalculos(Map<String, Double> detalhamentoCalculos) {
        this.detalhamentoCalculos = detalhamentoCalculos;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
}

