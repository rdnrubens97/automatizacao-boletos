package com.alra.service.service.cx230;

import com.alra.service.model.cx230.GeradorBoleto;
import com.alra.service.repository.cx230.GeradorBoletoRepository;
import com.alra.service.repository.cxempresa.BoletoDtoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

@Service
public class GeradorBoletoService {
    @Autowired
    GeradorBoletoRepository geradorBoletoRepository;
    @Autowired
    BoletoDtoRepository boletoDtoRepository;

    public void popularTblGeracaoBoleto(){
        boletoDtoRepository.deleteAll();
        geradorBoletoRepository.popularTblGeracaoBoleto();
    }

    public void atualizarColunaEmissaoBoletoTblKeySistema(Date dataVencimento, String seuNumero) {
        geradorBoletoRepository.atualizarColunaEmissaoBoletoTblKeySistema(dataVencimento, seuNumero);
    }

    public List<GeradorBoleto> listarGeradorBoleto(){
        List<GeradorBoleto> listaGeradorBoleto = geradorBoletoRepository.findAll();
        listaGeradorBoleto.forEach(geradorBoleto -> geradorBoleto.atualizarVencimento());
        return listaGeradorBoleto;
    }

}
