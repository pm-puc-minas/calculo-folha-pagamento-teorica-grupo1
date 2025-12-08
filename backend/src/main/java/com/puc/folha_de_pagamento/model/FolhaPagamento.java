package com.puc.folha_de_pagamento.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.puc.folha_de_pagamento.calculos.factory.CalculoFactory;
import com.puc.folha_de_pagamento.calculos.interfaces.IAdicional;
import com.puc.folha_de_pagamento.calculos.interfaces.IBeneficio;
import com.puc.folha_de_pagamento.calculos.interfaces.IDesconto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "folhas_pagamento")
public class FolhaPagamento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;
    
    @Column(nullable = false)
    private LocalDate dataReferencia;
    
    @Column(nullable = false)
    private double salarioBruto;
    
    @Column(nullable = false)
    private double salarioLiquido;
    
    @Transient
    private final List<IAdicional> adicionais;
    
    @Transient
    private final List<IDesconto> descontos;
    
    @Transient
    private final List<IBeneficio> beneficios;
    
    @Transient
    private final Map<String, Double> detalhamentoCalculos;
    
    public FolhaPagamento() {
        this.dataReferencia = LocalDate.now();
        this.adicionais = new ArrayList<>();
        this.descontos = new ArrayList<>();
        this.beneficios = new ArrayList<>();
        this.detalhamentoCalculos = new LinkedHashMap<>();
        inicializarCalculos();
    }
    
    private void inicializarCalculos() {
        CalculoFactory factory = CalculoFactory.getInstance();
        this.adicionais.addAll(factory.criarAdicionais());
        this.descontos.addAll(factory.criarDescontos());
        this.beneficios.addAll(factory.criarBeneficios());
    }
    
    public FolhaPagamento(Funcionario funcionario) {
        this();
        this.funcionario = funcionario;
        if (funcionario != null) {
            this.salarioBruto = funcionario.getSalarioBruto();
        }
    }
    
    public void calcularSalarioLiquido() {
        if (funcionario == null) {
            throw new IllegalStateException("Funcionário não pode ser nulo");
        }
        
        detalhamentoCalculos.clear();
        
        double totalAdicionais = adicionais.stream()
            .filter(adicional -> adicional.temDireito(funcionario))
            .mapToDouble(adicional -> {
                double valor = adicional.calcular(funcionario);
                detalhamentoCalculos.put(adicional.getDescricao(), valor);
                return valor;
            })
            .sum();
        
        double totalDescontos = descontos.stream()
            .filter(desconto -> desconto.isAplicavel(funcionario))
            .mapToDouble(desconto -> {
                double valor = desconto.calcular(funcionario, salarioBruto);
                detalhamentoCalculos.put(desconto.getDescricao(), -valor);
                return valor;
            })
            .sum();
        
        double descontosBeneficios = beneficios.stream()
            .filter(beneficio -> beneficio.temDireito(funcionario) && beneficio.isDesconto())
            .mapToDouble(beneficio -> {
                double valor = beneficio.calcular(funcionario);
                detalhamentoCalculos.put(beneficio.getDescricao(), -valor);
                return valor;
            })
            .sum();
        
        double adicionaisBeneficios = beneficios.stream()
            .filter(beneficio -> beneficio.temDireito(funcionario) && !beneficio.isDesconto())
            .mapToDouble(beneficio -> {
                double valor = beneficio.calcular(funcionario);
                detalhamentoCalculos.put(beneficio.getDescricao(), valor);
                return valor;
            })
            .sum();
        
        totalAdicionais += adicionaisBeneficios;
        totalDescontos += descontosBeneficios;
        
        salarioLiquido = salarioBruto + totalAdicionais - totalDescontos;
        
        double salarioHora = 0.0;
        if (funcionario.getJornadaTrabalho() != null) {
            salarioHora = funcionario.getJornadaTrabalho().calcularSalarioHora(salarioBruto);
        }
        
        detalhamentoCalculos.put("Salário por Hora", salarioHora);
        detalhamentoCalculos.put("Salário Bruto", salarioBruto);
        detalhamentoCalculos.put("Total Adicionais", totalAdicionais);
        detalhamentoCalculos.put("Total Descontos", -totalDescontos);
        detalhamentoCalculos.put("Salário Líquido", salarioLiquido);
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
    
    public Map<String, Double> getDetalhamentoCalculos() {
        return new LinkedHashMap<>(detalhamentoCalculos);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
}
