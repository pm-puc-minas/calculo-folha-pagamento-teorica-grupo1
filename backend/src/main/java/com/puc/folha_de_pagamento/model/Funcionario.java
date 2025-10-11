package com.puc.folha_de_pagamento.model;

import java.time.LocalDate;

public class Funcionario {
    
    private String nome;
    private String cpf;
    private String cargo;
    private double salarioBruto;
    private LocalDate dataAdmissao;
    private int numeroDependentes;
    private boolean temPericulosidade;
    private boolean temInsalubridade;
    private GrauInsalubridade grauInsalubridade;
    private double valorValeTransporte;
    private double valorValeAlimentacao;
    
    public Funcionario() {
        this.dataAdmissao = LocalDate.now();
        this.numeroDependentes = 0;
        this.temPericulosidade = false;
        this.temInsalubridade = false;
        this.grauInsalubridade = GrauInsalubridade.NENHUM;
        this.valorValeTransporte = 0.0;
        this.valorValeAlimentacao = 0.0;
    }
    
    public Funcionario(String nome, String cpf, String cargo, double salarioBruto) {
        this();
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.salarioBruto = salarioBruto;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getCargo() {
        return cargo;
    }
    
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
    public double getSalarioBruto() {
        return salarioBruto;
    }
    
    public void setSalarioBruto(double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }
    
    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }
    
    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }
    
    public int getNumeroDependentes() {
        return numeroDependentes;
    }
    
    public void setNumeroDependentes(int numeroDependentes) {
        this.numeroDependentes = numeroDependentes;
    }
    
    public boolean isTemPericulosidade() {
        return temPericulosidade;
    }
    
    public void setTemPericulosidade(boolean temPericulosidade) {
        this.temPericulosidade = temPericulosidade;
    }
    
    public boolean isTemInsalubridade() {
        return temInsalubridade;
    }
    
    public void setTemInsalubridade(boolean temInsalubridade) {
        this.temInsalubridade = temInsalubridade;
    }
    
    public GrauInsalubridade getGrauInsalubridade() {
        return grauInsalubridade;
    }
    
    public void setGrauInsalubridade(GrauInsalubridade grauInsalubridade) {
        this.grauInsalubridade = grauInsalubridade;
    }
    
    public double getValorValeTransporte() {
        return valorValeTransporte;
    }
    
    public void setValorValeTransporte(double valorValeTransporte) {
        this.valorValeTransporte = valorValeTransporte;
    }
    
    public double getValorValeAlimentacao() {
        return valorValeAlimentacao;
    }
    
    public void setValorValeAlimentacao(double valorValeAlimentacao) {
        this.valorValeAlimentacao = valorValeAlimentacao;
    }
    
    
    public enum GrauInsalubridade {
        NENHUM(0.0),
        BAIXO(0.10),
        MEDIO(0.20),
        ALTO(0.40);
        
        private final double percentual;
        
        GrauInsalubridade(double percentual) {
            this.percentual = percentual;
        }
        
        public double getPercentual() {
            return percentual;
        }
    }
}
