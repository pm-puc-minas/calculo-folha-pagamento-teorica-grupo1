package com.puc.folha_de_pagamento.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class JornadaTrabalho {
    
    private int horasMensais;
    private int horasDiarias;
    private int diasTrabalhados;
    
    public JornadaTrabalho() {
        this.horasMensais = 200;
        this.horasDiarias = 8;
        this.diasTrabalhados = 22;
    }
    
    public JornadaTrabalho(int horasMensais, int horasDiarias, int diasTrabalhados) {
        this.horasDiarias = horasDiarias;
        this.diasTrabalhados = diasTrabalhados;
        this.horasMensais = horasDiarias * diasTrabalhados;
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
        calcularHorasMensais();
    }
    
    public int getDiasTrabalhados() {
        return diasTrabalhados;
    }
    
    public void setDiasTrabalhados(int diasTrabalhados) {
        this.diasTrabalhados = diasTrabalhados;
        calcularHorasMensais();
    }
    
    private void calcularHorasMensais() {
        if (horasDiarias > 0 && diasTrabalhados > 0) {
            this.horasMensais = horasDiarias * diasTrabalhados;
        }
    }
    
    public double calcularSalarioHora(double salarioBruto) {
        if (horasMensais <= 0) {
            throw new IllegalArgumentException("Horas mensais devem ser maiores que zero");
        }
        return salarioBruto / horasMensais;
    }
}
