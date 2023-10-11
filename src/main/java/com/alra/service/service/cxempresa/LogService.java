package com.alra.service.service.cxempresa;

import com.alra.service.model.cxempresa.log.Log;
import com.alra.service.model.cxempresa.log.Tipo;
import com.alra.service.repository.cxempresa.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class LogService {
    @Autowired
    LogRepository logRepository;

    public void gerarLog(String idSistema, String mensagem, String tipo) {
        Log log = new Log();
        log.setDiaHora(new Date());
        log.setIdSistema(idSistema);
        log.setMensagem(mensagem);
        log.setTipo(tipo);
        logRepository.save(log);
    }

}
