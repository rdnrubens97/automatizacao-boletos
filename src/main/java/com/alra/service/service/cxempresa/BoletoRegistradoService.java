package com.alra.service.service.cxempresa;

import com.alra.service.model.cxempresa.boletoregistrado.BoletoRegistrado;
import com.alra.service.repository.cxempresa.BoletoRegistradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoletoRegistradoService {
    @Autowired
    BoletoRegistradoRepository boletoRegistradoRepository;

    public void salvarBoletoRegistrado(BoletoRegistrado boletoRegistrado) {
        boletoRegistradoRepository.save(boletoRegistrado);
    }

    public List<BoletoRegistrado> listarTodosBoletosRegistrados(){
        return boletoRegistradoRepository.findAll();
    }

    public List<BoletoRegistrado> selecionarBoletosRegistradosParaGerarXml(){
        List<BoletoRegistrado> boletosRegistrados = boletoRegistradoRepository.findByGeradoXmlFalse();
        return boletosRegistrados;
    }

    public void atualizarColunaGeradoXml(String codigoBarras){
        boletoRegistradoRepository.atualizarColunaGeradoXml(codigoBarras);
    }




}
