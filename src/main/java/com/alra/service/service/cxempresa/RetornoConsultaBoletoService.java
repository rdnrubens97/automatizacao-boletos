package com.alra.service.service.cxempresa;

import com.alra.service.model.cxempresa.retornoconsultaboleto.Content;
import com.alra.service.model.cxempresa.retornoconsultaboleto.RetornoConsultaBoleto;
import com.alra.service.repository.cxempresa.BoletoSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RetornoConsultaBoletoService {
    @Autowired
    LogService logService;
    @Autowired
    TokenInterService tokenInterService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    BoletoSistemaRepository boletoSistemaRepository;


    public RetornoConsultaBoleto consultarBoletos(String dataInicial, String dataFinal, String situacao, String filtrarDataPor, Integer paginaAtual) {
        // Atualiza o token de acesso antes de fazer a chamada
        tokenInterService.atualizarToken();

        // Busca o token de acesso atualizado
        String accessToken = tokenInterService.buscarAccessToken();

        // URL da API do Banco Inter para consultar boletos
        String url = "https://cdpj.partners.bancointer.com.br/cobranca/v2/boletos";

        // Configuração do cabeçalho da requisição
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + accessToken);

        // Construção da URI com parâmetros de consulta
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParam("dataInicial", dataInicial)
                .queryParam("dataFinal", dataFinal)
                .queryParam("situacao", situacao)
                .queryParam("filtrarDataPor", filtrarDataPor)
                .queryParam("itensPorPagina", 1000)
                .queryParam("paginaAtual", paginaAtual);  // Quantidade máxima de registros por página

        URI uri = builder.build().toUri();

        // Criação da requisição GET com os cabeçalhos e URI configurados
        RequestEntity<?> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

        // Realiza a chamada à API e retorna a resposta como uma ResponseEntity<List<RetornoConsultaBoleto>>
        ResponseEntity<RetornoConsultaBoleto> responseEntity = restTemplate.exchange(requestEntity, RetornoConsultaBoleto.class);

        return responseEntity.getBody();
    }

    public List<Content> buscarBoletosPagosNaApiDoBancoInter() {
        LocalDate hoje = LocalDate.now();
        LocalDate mesPassado = hoje.minusMonths(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String hojeFormatado = hoje.format(dateTimeFormatter);
        String mesPassadoFormatado = mesPassado.format(dateTimeFormatter);
        RetornoConsultaBoleto retornoConsultaBoleto = consultarBoletos(mesPassadoFormatado, hojeFormatado, "PAGO", "VENCIMENTO", 0);
        int numeroDePaginas = retornoConsultaBoleto.getTotalPages();
        List<Content> contentsSelecionadosComPagostatusDifDeUmAntesDaAtt = new ArrayList<>();
        if (numeroDePaginas == 1) {
            List<Content> contentList = retornoConsultaBoleto.getContent();
            for (Content content : contentList) {
                String nossoNumero = boletoSistemaRepository.findCodigoWithStatusNot1AndCodigoEquals(content.getNossoNumero());
                if (nossoNumero != null) {
                    contentsSelecionadosComPagostatusDifDeUmAntesDaAtt.add(content);
                }
            }
        } else {
            for (int i = 1; i < numeroDePaginas; i++) {
                RetornoConsultaBoleto paginaAtual = consultarBoletos("2023-08-01", "2023-08-25", "PAGO", "VENCIMENTO", i);
                List<Content> contentList = paginaAtual.getContent();
                for (Content content : contentList) {
                    String nossoNumero = boletoSistemaRepository.findCodigoWithStatusNot1AndCodigoEquals(content.getNossoNumero());
                    if (!(nossoNumero == null)) contentsSelecionadosComPagostatusDifDeUmAntesDaAtt.add(content);
                }
            }
        }
        return contentsSelecionadosComPagostatusDifDeUmAntesDaAtt;
    }

}
