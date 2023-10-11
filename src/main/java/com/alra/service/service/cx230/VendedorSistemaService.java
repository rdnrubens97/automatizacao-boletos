package com.alra.service.service.cx230;

import com.alra.service.model.cx230.LiberadorDeSistema;
import com.alra.service.model.cx230.VendedorSistema;
import com.alra.service.repository.cx230.VendedorSistemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class VendedorSistemaService {
    @Autowired
    VendedorSistemaRepository vendedorSistemaRepository;

    public List<VendedorSistema> listarVendedorSistema() {
        List<VendedorSistema> listaVendedorSistema = vendedorSistemaRepository.findAll();
        return listaVendedorSistema;
    }

    /**
     * Insere um registro de comissão para um vendedor no sistema.
     * @param liberadorDeSistema
     */
    public void insereComissao(LiberadorDeSistema liberadorDeSistema) {
        String idVendedorStr = liberadorDeSistema.getIdVendedor();
        if (idVendedorStr != null) {
            try {
                Integer idVendedor = Integer.parseInt(idVendedorStr);
                VendedorSistema vendedorSistema = new VendedorSistema();
                vendedorSistema.setIdVendedor(idVendedor);
                vendedorSistema.setValor(liberadorDeSistema.getComissao());
                vendedorSistema.setDataHora(new Date());
                vendedorSistema.setStatus(1);
                vendedorSistema.setChave(liberadorDeSistema.getIdSistema());
                vendedorSistema.setEmpresa(liberadorDeSistema.getNomeCliente());
                vendedorSistemaRepository.save(vendedorSistema);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

}
