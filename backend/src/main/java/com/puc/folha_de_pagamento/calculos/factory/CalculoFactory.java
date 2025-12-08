package com.puc.folha_de_pagamento.calculos.factory;

import java.util.ArrayList;
import java.util.List;

import com.puc.folha_de_pagamento.calculos.adicional.Insalubridade;
import com.puc.folha_de_pagamento.calculos.adicional.Periculosidade;
import com.puc.folha_de_pagamento.calculos.beneficio.FGTS;
import com.puc.folha_de_pagamento.calculos.beneficio.ValeAlimentacao;
import com.puc.folha_de_pagamento.calculos.beneficio.ValeTransporte;
import com.puc.folha_de_pagamento.calculos.desconto.INSS;
import com.puc.folha_de_pagamento.calculos.desconto.IRRF;
import com.puc.folha_de_pagamento.calculos.interfaces.IAdicional;
import com.puc.folha_de_pagamento.calculos.interfaces.IBeneficio;
import com.puc.folha_de_pagamento.calculos.interfaces.IDesconto;

public class CalculoFactory {
    
    private static CalculoFactory instance;
    
    private CalculoFactory() {
    }
    
    public static CalculoFactory getInstance() {
        if (instance == null) {
            synchronized (CalculoFactory.class) {
                if (instance == null) {
                    instance = new CalculoFactory();
                }
            }
        }
        return instance;
    }
    
    public List<IAdicional> criarAdicionais() {
        List<IAdicional> adicionais = new ArrayList<>();
        adicionais.add(new Periculosidade());
        adicionais.add(new Insalubridade());
        return adicionais;
    }
    
    public List<IDesconto> criarDescontos() {
        List<IDesconto> descontos = new ArrayList<>();
        descontos.add(new INSS());
        descontos.add(new IRRF());
        return descontos;
    }
    
    public List<IBeneficio> criarBeneficios() {
        List<IBeneficio> beneficios = new ArrayList<>();
        beneficios.add(new ValeTransporte());
        beneficios.add(new ValeAlimentacao());
        beneficios.add(new FGTS());
        return beneficios;
    }
    
    public IAdicional criarAdicional(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "PERICULOSIDADE" -> new Periculosidade();
            case "INSALUBRIDADE" -> new Insalubridade();
            default -> throw new IllegalArgumentException("Tipo de adicional não suportado: " + tipo);
        };
    }
    
    public IDesconto criarDesconto(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "INSS" -> new INSS();
            case "IRRF" -> new IRRF();
            default -> throw new IllegalArgumentException("Tipo de desconto não suportado: " + tipo);
        };
    }
    
    public IBeneficio criarBeneficio(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "VALE_TRANSPORTE", "VALETRANSPORTE" -> new ValeTransporte();
            case "VALE_ALIMENTACAO", "VALEALIMENTACAO" -> new ValeAlimentacao();
            case "FGTS" -> new FGTS();
            default -> throw new IllegalArgumentException("Tipo de benefício não suportado: " + tipo);
        };
    }
}

