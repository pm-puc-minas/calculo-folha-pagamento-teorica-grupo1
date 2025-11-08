package com.puc.folha_de_pagamento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puc.folha_de_pagamento.dto.FolhaPagamentoDTO;
import com.puc.folha_de_pagamento.eventos.RegistradorEventos;
import com.puc.folha_de_pagamento.model.FolhaPagamento;
import com.puc.folha_de_pagamento.model.Funcionario;
import com.puc.folha_de_pagamento.repository.FolhaPagamentoRepository;
import com.puc.folha_de_pagamento.repository.FuncionarioRepository;

@Service
@Transactional
public class FolhaPagamentoService {
    
    private final FolhaPagamentoRepository folhaPagamentoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final RegistradorEventos registradorEventos;
    
    @Autowired
    public FolhaPagamentoService(FolhaPagamentoRepository folhaPagamentoRepository,
                                 FuncionarioRepository funcionarioRepository) {
        this.folhaPagamentoRepository = folhaPagamentoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.registradorEventos = RegistradorEventos.getInstance();
    }
    
    public FolhaPagamentoDTO gerarFolhaPagamento(Long funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
        
        FolhaPagamento folha = new FolhaPagamento(funcionario);
        folha.calcularSalarioLiquido();
        folha = folhaPagamentoRepository.save(folha);
        
        registradorEventos.notificarFolhaGerada(folha);
        
        return new FolhaPagamentoDTO(folha);
    }
    
    public FolhaPagamentoDTO calcularFolha(Long folhaId) {
        FolhaPagamento folha = folhaPagamentoRepository.findById(folhaId)
            .orElseThrow(() -> new IllegalArgumentException("Folha de pagamento não encontrada"));
        
        folha.calcularSalarioLiquido();
        folha = folhaPagamentoRepository.save(folha);
        
        return new FolhaPagamentoDTO(folha);
    }
    
    public List<FolhaPagamento> listarFolhas() {
        return folhaPagamentoRepository.findAll();
    }
    
    public List<FolhaPagamento> listarFolhasPorFuncionario(Long funcionarioId) {
        return folhaPagamentoRepository.findByFuncionarioId(funcionarioId);
    }
    
    public Optional<FolhaPagamento> buscarPorId(Long id) {
        return folhaPagamentoRepository.findById(id);
    }
}

