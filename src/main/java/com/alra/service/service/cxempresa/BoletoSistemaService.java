package com.alra.service.service.cxempresa;

import com.alra.service.model.cxempresa.boletodto.BoletoDto;
import com.alra.service.model.cxempresa.boletosistema.BoletoSistema;
import com.alra.service.repository.cxempresa.BoletoSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoletoSistemaService {
    @Autowired
    BoletoSistemaRepository boletoSistemaRepository;

    public void salvarBoletoSistema(BoletoSistema boletoSistema) {
        boletoSistemaRepository.save(boletoSistema);
    }


}
