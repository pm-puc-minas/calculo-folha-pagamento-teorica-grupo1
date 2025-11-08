package com.puc.folha_de_pagamento.dto;

import com.puc.folha_de_pagamento.model.JornadaTrabalho;

public class JornadaTrabalhoDTO {
    
    private int horasMensais;
    private int horasDiarias;
    private int diasTrabalhados;
    
    public JornadaTrabalhoDTO() {
    }
    
    public JornadaTrabalhoDTO(JornadaTrabalho jornada) {
        if (jornada != null) {
            this.horasMensais = jornada.getHorasMensais();
            this.horasDiarias = jornada.getHorasDiarias();
            this.diasTrabalhados = jornada.getDiasTrabalhados();
        }
    }
    
    public JornadaTrabalho toEntity() {
        return new JornadaTrabalho(horasMensais, horasDiarias, diasTrabalhados);
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
}

