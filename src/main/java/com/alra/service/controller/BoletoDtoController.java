package com.alra.service.controller;

import com.alra.service.model.cxempresa.boletodto.BoletoDto;
import com.alra.service.model.cxempresa.retornoconsultaboleto.Content;
import com.alra.service.model.cxempresa.retornoconsultaboleto.RetornoConsultaBoleto;
import com.alra.service.repository.cx230.AuxMarketingBoleto230Repository;
import com.alra.service.repository.cxempresa.BoletoSistemaRepository;
import com.alra.service.service.cx230.MarketingService;
import com.alra.service.service.cxempresa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/boleto")
@EnableTransactionManagement
public class BoletoDtoController {
    @Autowired
    BoletoDtoService boletoDtoService;
    @Autowired
    RetornoConsultaBoletoService retornoConsultaBoletoService;


    @PostMapping("/ciclo-boleto")
    public ResponseEntity<Void> cicloCompletoEmissaoBoleto() {
        boletoDtoService.cicloCompletoEmissaoBoleto();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/atualizar-pagos")
    public ResponseEntity<Void> atualizarBoletosPagosNoSistema() {
        boletoDtoService.atualizarBoletosPagos();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/consultar-pagos")
    public ResponseEntity<RetornoConsultaBoleto> consultarBoletosPagos() {
        LocalDate hoje = LocalDate.now();
        LocalDate mesPassado = hoje.minusMonths(3);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String hojeFormatado = hoje.format(dateTimeFormatter);
        String mesPassadoFormatado = mesPassado.format(dateTimeFormatter);

        RetornoConsultaBoleto retornoConsultaBoleto = retornoConsultaBoletoService.consultarBoletos(mesPassadoFormatado, hojeFormatado, "PAGO", "VENCIMENTO", 0);
        List<Content> contentList = retornoConsultaBoleto.getContent();
        return ResponseEntity.ok().body(retornoConsultaBoleto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<BoletoDto>> listarBoletos() {
        List<BoletoDto> listaDeBoletoDtos = boletoDtoService.listarBoletos();
        return ResponseEntity.ok().body(listaDeBoletoDtos);
    }

    @GetMapping("/listar-mapper")
    public ResponseEntity<List<BoletoDto>> listaDeGeradadorBoletoParaBoleto() {
        List<BoletoDto> listaDeBoletoDtos = boletoDtoService.geradorBoletoListaParaBoletoDtoLista();
        return ResponseEntity.ok().body(listaDeBoletoDtos);
    }

    @PostMapping("/testando-testando")
    public void testandoLiberadorDeSistema() {


    }

}
