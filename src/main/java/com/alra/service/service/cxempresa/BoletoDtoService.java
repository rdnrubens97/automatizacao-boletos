package com.alra.service.service.cxempresa;

import com.alra.service.mapper.BoletoDtoMapper;
import com.alra.service.mapper.BoletoSistemaMapper;
import com.alra.service.model.cx230.GeradorBoleto;
import com.alra.service.model.cx230.LiberadorDeSistema;
import com.alra.service.model.cxempresa.boletodto.BoletoDto;
import com.alra.service.model.cxempresa.boletoregistrado.BoletoRegistrado;
import com.alra.service.model.cxempresa.boletosistema.BoletoSistema;
import com.alra.service.model.cxempresa.retornoconsultaboleto.Content;
import com.alra.service.repository.cxempresa.BoletoDtoRepository;
import com.alra.service.repository.cxempresa.BoletoRegistradoRepository;
import com.alra.service.repository.cxempresa.BoletoSistemaRepository;
import com.alra.service.service.cx230.GeradorBoletoService;
import com.alra.service.service.cx230.LiberadorDeSistemaService;
import com.alra.service.service.cx230.MarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BoletoDtoService {
    @Autowired
    LogService logService;
    @Autowired
    TokenInterService tokenInterService;
    @Autowired
    BoletoDtoRepository boletoDtoRepository;
    @Autowired
    BoletoRegistradoRepository boletoRegistradoRepository;
    @Autowired
    GeradorBoletoService geradorBoletoService;
    @Autowired
    BoletoRegistradoService boletoRegistradoService;
    @Autowired
    BoletoSistemaService boletoSistemaService;
    @Autowired
    RetornoConsultaBoletoService retornoConsultaBoletoService;
    @Autowired
    BoletoXmlService boletoXmlService;
    @Autowired
    LiberadorDeSistemaService liberadorDeSistemaService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    BoletoDtoMapper mapperGeradorBoletoParaBoletoDto;
    @Autowired
    BoletoSistemaMapper mapperBoletoRegistradoParaBoletoSistema;
    @Autowired
    MarketingService marketingService;
    @Autowired
    BoletoSistemaRepository boletoSistemaRepository;


    /**
     * Essa classe tem por objetivo gerar boletos e atualizar suas informações no banco de dados.
     * <p>
     * Tudo começa com a atualização do nosso token de comunicação com a API do banco Inter. O token fica no banco 'cxEmpresa' na 'tbl_token';
     * <p>
     * Feito a atualização do token, vamos popular a 'aux_geracao_boleto' que é uma tabela de suporte contendo todas informações que precisamos para gerar
     * um boleto. Essa tabela é preenchida por meio de um comando SQL personalizado presente na classe 'ComandosSqlCx230', o qual tem como referência a 'tbl_keysistema'
     * e verifica algumas colunas dessa tabela para selecionar um registro a ser preenchido na 'aux_geracao_boleto'. Para ser selecionado, o registro da 'tbl_keysistema'
     * deve conter o 'status' igual a '1', a coluna 'valor' deve ser diferente de '0', a coluna 'meses' deve ser '1' ou '0' e e coluna 'emissaoboleto' + '15' deve ser
     * menor que a data atual;
     * <p>
     * Populada a tabela 'aux_geracao_boleto' ja temos os clientes que atendem aos critérios para a emissão de um novo boleto, de modo que iremos mapear cada registro dessa
     * tabela para uma nova tabela 'aux_boleto_dto', do banco de dados 'CxEmpresa', esse mapeamento é feito por intermédio da classe 'BoletoDto'. Essa classe já está configurada
     * de acordo com a arquitetura exigida pela API do banco Inter, de modo que podemos enviar cada boleto da 'aux_boleto_dto' via JSON para a api do banco à fim de emitir
     * novos boletos, para tanto, chamamos o método 'emitirBoletos'.
     * <p>
     * Ao processar as informações enviadas via classe 'BoletoDto', a API do banco retornará as informações sobre o boleto que foi emitido e registrado um código de barras a ser
     * faturado. A classe que mapeia o JSON retornado pela API do banco para esse boleto válido é a classe 'BoletoRegistrado', esses dados são salvos na tabela
     * 'aux_boleto_registrado' no banco 'CxEmpresa'.
     * <p>
     * Tendo as informações do boleto registrado e válido, geraremos um XML com suas informações onde personalizaremos um boleto com o logo da AlraSistemas e que, depois,
     * será enviado no e-mail do cliente. Esse XML gerado em cima de cada boleto registrado será salvo na tabela 'aux_boleto_xml' no banco 'CxEmpresa' e enviado via FTP para
     * o endereço remoto.
     *
     * @return
     */
    //Método principal
    public void cicloCompletoEmissaoBoleto() {
        tokenInterService.atualizarToken();
        geradorBoletoService.popularTblGeracaoBoleto();
        List<BoletoDto> listaBoletosDto = geradorBoletoListaParaBoletoDtoLista();
        if (!listaBoletosDto.isEmpty()) emitirBoletos(listaBoletosDto);
        boletoXmlService.gerarXmlESalvarNaAuxBoletoXml();
        boletoXmlService.boletoXmlParaPdf();
        marketingService.enviarWhatsApp();
    }

    public void atualizarBoletosPagos() {
        List<Content> listaContent = retornoConsultaBoletoService.buscarBoletosPagosNaApiDoBancoInter();
        liberadorDeSistemaService.popularTblLiberadorDeSistema(listaContent);
        List<LiberadorDeSistema> listaLiberadorSistema = liberadorDeSistemaService.listarLiberadorSistema();
        if (!listaLiberadorSistema.isEmpty()) {
            for (LiberadorDeSistema liberadorDeSistema : listaLiberadorSistema) {
                liberadorDeSistemaService.atualizacoesSistemaBoletoPago(liberadorDeSistema);
            }
            if (!listaContent.isEmpty()) {
                for (Content content : listaContent) {
                    try {
                        boletoSistemaRepository.atualizarDadosBoletosPagosTblBoleto(content);
                        logService.gerarLog(content.getSeuNumero(), "Sucesso ao atualizar boleto pago de código " + content.getNossoNumero(), "ATUALIZACAO BOLETO PAGO");
                    } catch (Exception e) {
                        logService.gerarLog(content.getSeuNumero(), "Sucesso ao atualizar boleto pago de código " + content.getNossoNumero(), "ATUALIZACAO BOLETO PAGO");
                    }
                }
            }
        }
    }

    //Métodos auxiliares

    /**
     * emite boletoRegistrado iterando sobre uma lista de BoletoDto. Para cada boleto da lista é chamado o método 'requisitarEmissaoDeUmBoleto'.
     * Esse método retorna uma String para cada boleto emitido, a qual é armazenada no StringBuilder e depois retornada.
     *
     * @param boletoDtos
     * @return retorna uma string contendo informações sobre a emissão dos boletoDtos.
     */
    public void emitirBoletos(List<BoletoDto> boletoDtos) {
        for (BoletoDto boletoDto : boletoDtos) {
            requisitarEmissaoDeUmBoleto(boletoDto);
        }
    }

    /**
     * Emite um boletoDto da classe BoletoRegistrado, se comunicando com a API do banco Inter.
     *
     * @param boletoDto
     * @return retorna uma string contendo informações sobre a emissão de um boletoDto.
     */
    public void requisitarEmissaoDeUmBoleto(BoletoDto boletoDto) {
        String COBRANCA_URL = "https://cdpj.partners.bancointer.com.br/cobranca/v2/boletos";
        String accessToken = tokenInterService.buscarAccessToken();
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<BoletoDto> requestEntity = new HttpEntity<>(boletoDto, headers);
            ResponseEntity<BoletoRegistrado> response = restTemplate.postForEntity(COBRANCA_URL, requestEntity, BoletoRegistrado.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                // Assim que tivermos uma emissão de boleto bem sucedida, atualizaremos a coluna 'emissaoboleto' da 'tbl_keysistema' com a data de vencimento
                // do boleto emitido. Desse modo, evitamos que sejam emitidos dois boletos para em um mesmo período para o mesmo cliente. Também, salvamos o
                // boleto registrado na aux_boleto_registrado
                BoletoRegistrado boletoRegistrado = response.getBody();
                boletoRegistrado.atualizarBoletoRegistradoComBoleto(boletoDto);
                boletoRegistradoService.salvarBoletoRegistrado(boletoRegistrado);
                geradorBoletoService.atualizarColunaEmissaoBoletoTblKeySistema(boletoRegistrado.getDataVencimento(), boletoRegistrado.getSeuNumero());
                //Aqui vai o envio por e-mail do boleto

                logService.gerarLog(boletoDto.getSeuNumero(), "Boleto código " + boletoRegistrado.getNossoNumero() + " emitido com sucesso", "BOLETO EMITIDO");

                // Mapeamos o boletoRegistrado para o boletoSistema e salvamos no banco de dados
                BoletoSistema boletoSistema = mapperBoletoRegistradoParaBoletoSistema.mapearBoletoRegistradoParaBoletoSistema(boletoRegistrado);
                boletoSistema.setLink();
                boletoSistemaService.salvarBoletoSistema(boletoSistema);
            } else {
                String responseBody = response.getBody() != null ? response.getBody().toString() : "Sem resposta do servidor";
                logService.gerarLog(boletoDto.getSeuNumero(), "Erro ao emitir o boleto: " + responseBody, "ERRO EMISSAO BOLETO");
            }
        } catch (Exception e) {
            logService.gerarLog(boletoDto.getSeuNumero(), "Ocorreu um erro inesperado: " + e.getMessage(), "ERRO EMISSAO BOLETO");
        }
    }

    public List<BoletoDto> geradorBoletoListaParaBoletoDtoLista() {
        List<GeradorBoleto> listaGeradorBoleto = geradorBoletoService.listarGeradorBoleto();
        if (!listaGeradorBoleto.isEmpty()) {
            List<BoletoDto> listaBoletoDto = new ArrayList<>();
            for (GeradorBoleto geradorBoleto : listaGeradorBoleto) {
                BoletoDto boletoDto = mapperGeradorBoletoParaBoletoDto.mapearParaBoleto(geradorBoleto);
                boletoDtoRepository.save(boletoDto);
                listaBoletoDto.add(boletoDto);
            }
            return listaBoletoDto;
        }
        return Collections.emptyList();
    }

    public List<BoletoDto> listarBoletos() {
        return boletoDtoRepository.findAll();
    }

    public BoletoRegistrado buscarBoletoRegistrado() {
        return boletoRegistradoRepository.findAll().get(0);
    }

    public List<BoletoRegistrado> teste() {
        List<BoletoRegistrado> boletos = boletoRegistradoService.selecionarBoletosRegistradosParaGerarXml();
        return boletos;
    }


}
