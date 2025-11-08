package com.puc.folha_de_pagamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.puc.folha_de_pagamento.model.FolhaPagamento;
import com.puc.folha_de_pagamento.model.Funcionario;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long> {
    
    List<FolhaPagamento> findByFuncionario(Funcionario funcionario);
    
    List<FolhaPagamento> findByFuncionarioId(Long funcionarioId);
    
}

