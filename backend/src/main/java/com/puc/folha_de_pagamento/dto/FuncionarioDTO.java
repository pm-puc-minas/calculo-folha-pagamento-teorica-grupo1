package com.puc.folha_de_pagamento.dto;

import java.time.LocalDate;

import com.puc.folha_de_pagamento.model.Funcionario;

public class FuncionarioDTO {
    
    private String nome;
    private String cpf;
    private String cargo;
    private double salarioBruto;
    private LocalDate dataAdmissao;
    private int numeroDependentes;
    private boolean temPericulosidade;
    private boolean temInsalubridade;
    private String grauInsalubridade;
    private double valorValeTransporte;
    private double valorValeAlimentacao;
    private JornadaTrabalhoDTO jornadaTrabalho;
    
    public FuncionarioDTO() {
    }
    
    public FuncionarioDTO(Funcionario funcionario) {
        if (funcionario != null) {
            this.nome = funcionario.getNome();
            this.cpf = funcionario.getCpf();
            this.cargo = funcionario.getCargo();
            this.salarioBruto = funcionario.getSalarioBruto();
            this.dataAdmissao = funcionario.getDataAdmissao();
            this.numeroDependentes = funcionario.getNumeroDependentes();
            this.temPericulosidade = funcionario.isTemPericulosidade();
            this.temInsalubridade = funcionario.isTemInsalubridade();
            this.grauInsalubridade = funcionario.getGrauInsalubridade() != null 
                ? funcionario.getGrauInsalubridade().name() 
                : null;
            this.valorValeTransporte = funcionario.getValorValeTransporte();
            this.valorValeAlimentacao = funcionario.getValorValeAlimentacao();
            if (funcionario.getJornadaTrabalho() != null) {
                this.jornadaTrabalho = new JornadaTrabalhoDTO(funcionario.getJornadaTrabalho());
            }
        }
    }
    
    public Funcionario toEntity() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(this.nome);
        funcionario.setCpf(this.cpf);
        funcionario.setCargo(this.cargo);
        funcionario.setSalarioBruto(this.salarioBruto);
        funcionario.setDataAdmissao(this.dataAdmissao);
        funcionario.setNumeroDependentes(this.numeroDependentes);
        funcionario.setTemPericulosidade(this.temPericulosidade);
        funcionario.setTemInsalubridade(this.temInsalubridade);
        if (this.grauInsalubridade != null) {
            funcionario.setGrauInsalubridade(Funcionario.GrauInsalubridade.valueOf(this.grauInsalubridade));
        }
        funcionario.setValorValeTransporte(this.valorValeTransporte);
        funcionario.setValorValeAlimentacao(this.valorValeAlimentacao);
        if (this.jornadaTrabalho != null) {
            funcionario.getJornadaTrabalho().setHorasMensais(this.jornadaTrabalho.getHorasMensais());
            funcionario.getJornadaTrabalho().setHorasDiarias(this.jornadaTrabalho.getHorasDiarias());
            funcionario.getJornadaTrabalho().setDiasTrabalhados(this.jornadaTrabalho.getDiasTrabalhados());
        }
        return funcionario;
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
    
    public String getGrauInsalubridade() {
        return grauInsalubridade;
    }
    
    public void setGrauInsalubridade(String grauInsalubridade) {
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
    
    public JornadaTrabalhoDTO getJornadaTrabalho() {
        return jornadaTrabalho;
    }
    
    public void setJornadaTrabalho(JornadaTrabalhoDTO jornadaTrabalho) {
        this.jornadaTrabalho = jornadaTrabalho;
    }
}

