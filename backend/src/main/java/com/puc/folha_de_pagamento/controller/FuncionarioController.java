package com.puc.folha_de_pagamento.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puc.folha_de_pagamento.dto.FuncionarioDTO;
import com.puc.folha_de_pagamento.model.Funcionario;
import com.puc.folha_de_pagamento.service.FuncionarioService;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {
    
    private final FuncionarioService funcionarioService;
    
    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }
    
    @PostMapping
    public ResponseEntity<FuncionarioDTO> cadastrarFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            Funcionario funcionario = funcionarioService.cadastrarFuncionario(funcionarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new FuncionarioDTO(funcionario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> atualizarFuncionario(
            @PathVariable Long id,
            @RequestBody FuncionarioDTO funcionarioDTO) {
        try {
            Funcionario atualizado = funcionarioService.atualizarFuncionario(id, funcionarioDTO);
            return ResponseEntity.ok(new FuncionarioDTO(atualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> listarFuncionarios() {
        List<FuncionarioDTO> funcionarios = funcionarioService.listarFuncionarios().stream()
            .map(FuncionarioDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(funcionarios);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Long id) {
        return funcionarioService.buscarPorId(id)
            .map(funcionario -> ResponseEntity.ok(new FuncionarioDTO(funcionario)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
        try {
            funcionarioService.deletarFuncionario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

