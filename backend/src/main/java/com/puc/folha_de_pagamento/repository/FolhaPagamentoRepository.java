package com.puc.folha_de_pagamento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.puc.folha_de_pagamento.model.FolhaPagamento;
import com.puc.folha_de_pagamento.model.Funcionario;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long> {
    
    List<FolhaPagamento> findByFuncionario(Funcionario funcionario);
    
    @Query("SELECT f FROM FolhaPagamento f JOIN FETCH f.funcionario WHERE f.funcionario.id = :funcionarioId")
    List<FolhaPagamento> findByFuncionarioId(Long funcionarioId);
    
    @Query("SELECT f FROM FolhaPagamento f JOIN FETCH f.funcionario")
    @Override
    List<FolhaPagamento> findAll();
    
}

