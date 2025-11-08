package com.puc.folha_de_pagamento.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.puc.folha_de_pagamento.dto.FolhaPagamentoDTO;
import com.puc.folha_de_pagamento.service.FolhaPagamentoService;

@RestController
@RequestMapping("/api/folhas")
public class FolhaPagamentoController {
    
    private final FolhaPagamentoService folhaPagamentoService;
    
    @Autowired
    public FolhaPagamentoController(FolhaPagamentoService folhaPagamentoService) {
        this.folhaPagamentoService = folhaPagamentoService;
    }
    
    @PostMapping("/gerar/{funcionarioId}")
    public ResponseEntity<FolhaPagamentoDTO> gerarFolhaPagamento(@PathVariable Long funcionarioId) {
        try {
            FolhaPagamentoDTO folhaDTO = folhaPagamentoService.gerarFolhaPagamento(funcionarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(folhaDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/{id}/calcular")
    public ResponseEntity<FolhaPagamentoDTO> calcularFolha(@PathVariable Long id) {
        try {
            FolhaPagamentoDTO folhaDTO = folhaPagamentoService.calcularFolha(id);
            return ResponseEntity.ok(folhaDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping
    public ResponseEntity<List<FolhaPagamentoDTO>> listarFolhas() {
        List<FolhaPagamentoDTO> folhas = folhaPagamentoService.listarFolhas().stream()
            .map(FolhaPagamentoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(folhas);
    }
    
    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<FolhaPagamentoDTO>> listarFolhasPorFuncionario(@PathVariable Long funcionarioId) {
        List<FolhaPagamentoDTO> folhas = folhaPagamentoService.listarFolhasPorFuncionario(funcionarioId).stream()
            .map(FolhaPagamentoDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(folhas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FolhaPagamentoDTO> buscarPorId(@PathVariable Long id) {
        return folhaPagamentoService.buscarPorId(id)
            .map(folha -> ResponseEntity.ok(new FolhaPagamentoDTO(folha)))
            .orElse(ResponseEntity.notFound().build());
    }
}

