package com.puc.folha_de_pagamento.model;

public class JornadaTrabalho {
    
    private int horasMensais;
    private int horasDiarias;
    private int diasTrabalhados;
    
    public JornadaTrabalho() {
        this.horasMensais = 220; // Padrão CLT
        this.horasDiarias = 8;
        this.diasTrabalhados = 22;
    }
    
    public JornadaTrabalho(int horasMensais, int horasDiarias, int diasTrabalhados) {
        this.horasMensais = horasMensais;
        this.horasDiarias = horasDiarias;
        this.diasTrabalhados = diasTrabalhados;
    }
    
    public int getHorasMensais() {
        return horasMensais;
    }
    
    public void setHorasMensais(int horasMensais) {
        this.horasMensais = horasMensais;
    }
    
    public int getHorasDiarias() {
        return horasDiarias;
    }
    
    public void setHorasDiarias(int horasDiarias) {
        this.horasDiarias = horasDiarias;
    }
    
    public int getDiasTrabalhados() {
        return diasTrabalhados;
    }
    
    public void setDiasTrabalhados(int diasTrabalhados) {
        this.diasTrabalhados = diasTrabalhados;
    }
    
    public double calcularSalarioHora(double salarioBruto) {
        if (horasMensais <= 0) {
            throw new IllegalArgumentException("Horas mensais devem ser maiores que zero");
        }
        return salarioBruto / horasMensais;
    }
}
