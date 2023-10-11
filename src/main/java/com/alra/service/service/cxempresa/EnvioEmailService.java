package com.alra.service.service.cxempresa;

import com.alra.service.model.cxempresa.envioemail.EnvioEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnvioEmailService {
    @Autowired
    EnvioEmail envioEmail;
    @Autowired
    FtpService ftpService;
    @Autowired
    LogService logService;

    public void enviarBoletoPorEmail(String destinatario, byte[] anexo, String nomeBoleto, String nossoNumero, String seuNumero) {
        if (!destinatario.isEmpty()){
            try {
                String assunto = "Boleto Empresa X";
                String corpo = "Segue o boleto do programa X em anexo, o mesmo também pode ser obtido em seu sistema";

                envioEmail.enviarEmailComAnexo(destinatario.toLowerCase(), assunto, corpo, anexo, nomeBoleto);
                logService.gerarLog(seuNumero, "Boleto com código " + nossoNumero + " enviado por e-mail", "ENVIO E-MAIL REALIZADO");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logService.gerarLog(seuNumero, "Boleto com código " + nossoNumero + " NÃO enviado", "ERRO ENVIO E-MAIL");
        }
    }


}
