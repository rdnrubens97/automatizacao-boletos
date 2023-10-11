package com.alra.service.service.cx230;

import com.alra.service.model.cx230.AuxMarketingBoleto230;
import com.alra.service.model.cx230.AuxMarketingEnvioChave;
import com.alra.service.model.cx230.HttpConsumer;
import com.alra.service.model.cx230.Marketing;
import com.alra.service.model.cxempresa.boletosistema.BoletoSistema;
import com.alra.service.repository.cx230.AuxMarketingBoleto230Repository;
import com.alra.service.repository.cx230.AuxMarketingEnvioChaveRepository;
import com.alra.service.repository.cx230.KeySistemaRepository;
import com.alra.service.repository.cx230.MarketingRepository;
import com.alra.service.repository.cxempresa.BoletoSistemaRepository;
import com.alra.service.service.cxempresa.FeedbackService;
import com.alra.service.service.cxempresa.LogService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MarketingService {
    @Autowired
    HttpConsumer httpConsumer;
    @Autowired
    LogService logService;
    @Autowired
    AuxMarketingBoleto230Repository auxMarketingBoleto230Repository;
    @Autowired
    AuxMarketingEnvioChaveRepository auxMarketingEnvioChaveRepository;
    @Autowired
    MarketingRepository marketingRepository;
    @Autowired
    KeySistemaRepository keySistemaRepository;
    @Autowired
    BoletoSistemaRepository boletoSistemaRepository;
    @Autowired
    FeedbackService feedbackService;


    private final String API_KEY = "3ff53f57-8314-4011-8041-7de428c63b59";
    private final String mensagemCertificadoTexto = "Bom dia,%0ANós da AlraSistemas também somos certificadores, podemos emitir e renovar seu *Certificado Digital* por vídeo conferência, valor *R$ 195,00* e cobrado junto na mensalidade do sistema.%0AFaça o seu certificado no conforto de sua casa ou empresa, com o atendimento Alra Sistemas que você já conhece. %0APedido feito aqui mesmo no whatsapp.";
    private final String mensagemCertificadoImagem = "https://www.alrasistemas.com.br/baixar/certificado.png";
    private final String mensagemBoleto = "*MENSAGEM AUTOMÁTICA*%0ABom dia, não precisa responder.%0ASegue o boleto do sistema, caso não queira mais receber o boleto via whatsapp, favor informar.";

    public void cicloDeMarketing() {
//        enviarWhatsApp();
        envioBoletoWhatsApp();
//        feedbackService.recebendoFeedback();
//        enviarChave();
    }

    public void enviarWhatsApp() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime horarioInicial = LocalDateTime.of(agora.toLocalDate(), LocalTime.of(8, 10));
        LocalDateTime horarioFinal = LocalDateTime.of(agora.toLocalDate(), LocalTime.of(18, 0));

        if (agora.compareTo(horarioInicial) >= 0 && agora.compareTo(horarioFinal) <= 0) {
            List<Marketing> listaMarketing = marketingRepository.findAll();

            for (Marketing marketing : listaMarketing) {
                String tel1 = marketing.getTel1();
                String idSistema = marketing.getIdSistema();
                try {
                    if (tel1.length() > 8) {
                        Thread.sleep(1000);
                        httpConsumer.requisicaoGet(enviarWhatsApp(1, tel1, mensagemCertificadoImagem));
                        Thread.sleep(1000);
                        httpConsumer.requisicaoGet(enviarWhatsApp(0, tel1, mensagemCertificadoTexto));
                        keySistemaRepository.updateKeySistemaCertificadoByIdSistema(idSistema);
                        logService.gerarLog(idSistema, "Envio WhatsApp realizado com sucesso para telefone: "
                                + marketing.getTel1() + " cliente " + marketing.getNomeCliente(), "ENVIO MARKETING WHATSAPP");
                    }
                } catch (Exception e) {
                    logService.gerarLog(idSistema, "Ocorreu um erro para envio WhatsApp telefone: "
                            + marketing.getTel1() + " cliente " + marketing.getNomeCliente() + ".Erro: " + e.getMessage(), "ERRO ENVIO MARKETING WHATSAPP");
                    e.printStackTrace();
                }
            }
        }
    }

    @SneakyThrows
    public void envioBoletoWhatsApp() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime horarioInicial = LocalDateTime.of(agora.toLocalDate(), LocalTime.of(8, 10));
        LocalDateTime horarioFinal = LocalDateTime.of(agora.toLocalDate(), LocalTime.of(18, 0));

        if (agora.compareTo(horarioInicial) >= 0 && agora.compareTo(horarioFinal) <= 0) {
            List<BoletoSistema> boletosSelecionados = boletoSistemaRepository.selecionarBoletosParaEnvioMarketingBoleto();
            if (!boletosSelecionados.isEmpty()) {
                for (BoletoSistema boletoSistema : boletosSelecionados) {
                    String id = boletoSistema.getIdSistema();
                    String link = boletoSistema.getLink();
                    keySistemaRepository.selecionarDadosKeySistemaParaMarketingBoleto(id);
                    List<AuxMarketingBoleto230> auxMarketingBoleto230List = auxMarketingBoleto230Repository.findAll();
                    if (!auxMarketingBoleto230List.isEmpty()) {
                        for (AuxMarketingBoleto230 auxMarketingBoleto230 : auxMarketingBoleto230List) {
                            String idSistema = auxMarketingBoleto230.getIdSistema();
                            String tel1 = auxMarketingBoleto230.getTel1();
                            if (tel1.length() > 8) {
                                httpConsumer.requisicaoGet(enviarWhatsApp(0, tel1, mensagemBoleto));
                                Thread.sleep(5000);
                                httpConsumer.requisicaoGet(enviarWhatsApp(2, tel1, link));
                                boletoSistemaRepository.atualizarEnvioWhatsApp(idSistema);
                                logService.gerarLog(idSistema, "WhatsApp boleto enviado.", "ENVIO WHATSAPP");
                            } else {
                                logService.gerarLog(idSistema, "WhatsApp boleto não enviado por ausência de telefone no cadastro.", "FALHA NO ENVIO WHATSAPP");
                            }
                        }
                    }
                }
            }
        }
    }

    @SneakyThrows
    public void enviarChave() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime horarioInicial = LocalDateTime.of(agora.toLocalDate(), LocalTime.of(8, 10));
        LocalDateTime horarioFinal = LocalDateTime.of(agora.toLocalDate(), LocalTime.of(18, 0));

        if (agora.compareTo(horarioInicial) >= 0 && agora.compareTo(horarioFinal) <= 0) {
            List<AuxMarketingEnvioChave> auxMarketingEnvioChaveList = auxMarketingEnvioChaveRepository.findAll();
            for (AuxMarketingEnvioChave auxMarketingEnvioChave : auxMarketingEnvioChaveList) {
                String idSistema = auxMarketingEnvioChave.getIdSistema();
                String tel1 = auxMarketingEnvioChave.getTel1();
                if (tel1.length() > 8) {
                    String mensagem = "Bom%20dia%2C%20caso%20esteja%20sem%20internet%2C%20a%20chave%20do%20sistema%20%C3%A9%20*" + auxMarketingEnvioChave.getCodManual() +
                            "*%0APara%20fazer%20o%20procedimento%2C%20basta%20ir%20no%20menu%20%22Administrativo%22%20do%20sistema%2C%20depois%20%22Licen%C3%A7a%20do%20sistema%22%2C%20e%20colocar%20o%20c%C3%B3digo.";
                    httpConsumer.requisicaoGet(enviarWhatsApp(0, tel1, mensagem));
                    Thread.sleep(5000);
                    auxMarketingEnvioChaveRepository.atualizarPendenteKeySistema(idSistema);
                    logService.gerarLog(idSistema, "Chave sistema enviada por WhatsApp.", "ENVIO WHATSAPP CHAVE SISTEMA");
                } else {
                    logService.gerarLog(idSistema, "Chave sistema não enviada por ausência de telefone no cadastro. ", "FALHA NO ENVIO WHATSAPP CHAVE SISTEMA");
                }
            }
        }
    }

    private String enviarWhatsApp(int opcao, String tel, String mensagem) {
        String tipo;
        switch (opcao) {
            case 1:
                tipo = "image";
                break;
            case 2:
                tipo = "file";
                break;
            default:
                tipo = "text";
        }
        String retorno = "https://app.zapwork.com.br/painel/notificacoes_get?api_key=" + API_KEY + "&"
                + "type=" + tipo + "&number=55" + tel + "&message=" + mensagem;
        return retorno.replace(" ", "%20");
    }


}
