package com.puc.folha_de_pagamento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.puc.folha_de_pagamento.dto.FuncionarioDTO;
import com.puc.folha_de_pagamento.eventos.RegistradorEventos;
import com.puc.folha_de_pagamento.model.Funcionario;
import com.puc.folha_de_pagamento.repository.FolhaPagamentoRepository;
import com.puc.folha_de_pagamento.repository.FuncionarioRepository;

@Service
@Transactional
public class FuncionarioService {
    
    private final FuncionarioRepository funcionarioRepository;
    private final FolhaPagamentoRepository folhaPagamentoRepository;
    private final RegistradorEventos registradorEventos;
    
    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository,
                              FolhaPagamentoRepository folhaPagamentoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.folhaPagamentoRepository = folhaPagamentoRepository;
        this.registradorEventos = RegistradorEventos.getInstance();
    }
    
    public Funcionario cadastrarFuncionario(FuncionarioDTO funcionarioDTO) {
        if (funcionarioDTO == null) {
            throw new IllegalArgumentException("Funcionário DTO não pode ser nulo");
        }
        
        Funcionario funcionario = funcionarioDTO.toEntity();
        validarFuncionario(funcionario);
        
        if (funcionarioRepository.findByCpf(funcionario.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um funcionário com este CPF");
        }
        
        Funcionario funcionarioSalvo = funcionarioRepository.save(funcionario);
        registradorEventos.logFuncionarioCadastrado(funcionarioSalvo);
        
        return funcionarioSalvo;
    }
    
    public Funcionario atualizarFuncionario(Long id, FuncionarioDTO funcionarioDTO) {
        if (id == null || funcionarioDTO == null) {
            throw new IllegalArgumentException("ID e DTO não podem ser nulos");
        }
        
        Funcionario funcionario = funcionarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
        
        return atualizarFuncionario(funcionario, funcionarioDTO);
    }
    
    private Funcionario atualizarFuncionario(Funcionario funcionario, FuncionarioDTO funcionarioDTO) {
        funcionario.setNome(funcionarioDTO.getNome());
        funcionario.setCpf(funcionarioDTO.getCpf());
        funcionario.setCargo(funcionarioDTO.getCargo());
        funcionario.setSalarioBruto(funcionarioDTO.getSalarioBruto());
        funcionario.setNumeroDependentes(funcionarioDTO.getNumeroDependentes());
        funcionario.setTemPericulosidade(funcionarioDTO.isTemPericulosidade());
        funcionario.setTemInsalubridade(funcionarioDTO.isTemInsalubridade());
        
        if (funcionarioDTO.getGrauInsalubridade() != null) {
            funcionario.setGrauInsalubridade(
                Funcionario.GrauInsalubridade.valueOf(funcionarioDTO.getGrauInsalubridade())
            );
        }
        
        funcionario.setValorValeTransporte(funcionarioDTO.getValorValeTransporte());
        funcionario.setValorValeAlimentacao(funcionarioDTO.getValorValeAlimentacao());
        
        validarFuncionario(funcionario);
        
        return funcionarioRepository.save(funcionario);
    }
    
    public List<Funcionario> listarFuncionarios() {
        return funcionarioRepository.findAll();
    }
    
    public Optional<Funcionario> buscarPorId(Long id) {
        return funcionarioRepository.findById(id);
    }
    
    public Optional<Funcionario> buscarPorCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf);
    }
    
    public void deletarFuncionario(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado"));
        
        folhaPagamentoRepository.deleteAll(folhaPagamentoRepository.findByFuncionarioId(id));
        
        funcionarioRepository.deleteById(id);
        registradorEventos.logFuncionarioDeletado(funcionario);
    }
    
    private void validarFuncionario(Funcionario funcionario) {
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do funcionário é obrigatório");
        }
        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do funcionário é obrigatório");
        }
        if (funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo do funcionário é obrigatório");
        }
        if (funcionario.getDataAdmissao() == null) {
            throw new IllegalArgumentException("Data de admissão é obrigatória");
        }
        if (funcionario.getSalarioBruto() < 0) {
            throw new IllegalArgumentException("Salário bruto não pode ser negativo");
        }
    }
}

