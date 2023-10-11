package com.alra.service.model.cxempresa.envioemail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class EnvioEmail {
    @Autowired
    private JavaMailSender javaMailSender;
    private String emailRemetente = "financeiro@alrasistemas.com.br";

    public void enviarEmailComAnexo(String destinatario, String assunto, String corpo, byte[] anexo, String nomeAnexo) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(emailRemetente);
        helper.setTo(destinatario);
        helper.setSubject(assunto);
        helper.setText(corpo);
        helper.addAttachment(Objects.requireNonNull(nomeAnexo), new ByteArrayResource(anexo));

        javaMailSender.send(message);
    }

//        public void enviarEmailComAnexo(String destinatario, String assunto, String corpo, byte[] anexo, String nomeAnexo) throws MessagingException {
//        MimeMessage mail = javaMailSender.createMimeMessage();
//
//        MimeMessageHelper message = new MimeMessageHelper(mail);
//        message.setTo(destinatario);
//        message.setSubject(assunto);
//        message.setText(corpo);
//        message.setFrom(emailRemetente);
//    }



}
