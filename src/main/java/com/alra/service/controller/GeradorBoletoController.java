package com.alra.service.controller;

import com.alra.service.model.cx230.GeradorBoleto;
import com.alra.service.service.cx230.GeradorBoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gerador-boleto")
public class GeradorBoletoController {
    @Autowired
    GeradorBoletoService geradorBoletoService;

    @GetMapping("/popular")
    public ResponseEntity<String> popularTblGeracaoBoleto() {
        try {
            geradorBoletoService.popularTblGeracaoBoleto();
            return ResponseEntity.ok().body("");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao popular 'tbl_geracao_boleto: " + e.getMessage());
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<GeradorBoleto>> listarGeradorBoleto() {
        List<GeradorBoleto> listaGeradorBoleto = geradorBoletoService.listarGeradorBoleto();
        return ResponseEntity.ok().body(listaGeradorBoleto);
    }


}
