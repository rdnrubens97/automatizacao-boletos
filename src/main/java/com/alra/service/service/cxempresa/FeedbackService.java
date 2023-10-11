package com.alra.service.service.cxempresa;

import com.alra.service.model.cx230.KeySistema;
import com.alra.service.model.cx230.Vendedor;
import com.alra.service.model.cxempresa.cliente.Cliente;
import com.alra.service.model.cxempresa.representante.Representante;
import com.alra.service.repository.cx230.KeySistemaRepository;
import com.alra.service.repository.cx230.VendedorRepository;
import com.alra.service.repository.cxempresa.ClienteRepository;
import com.alra.service.repository.cxempresa.RepresentanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    KeySistemaRepository keySistemaRepository;
    @Autowired
    RepresentanteRepository representanteRepository;
    @Autowired
    VendedorRepository vendedorRepository;


    @Modifying
    @Transactional
    public void recebendoFeedback() {
        List<Cliente> listaDeClientes = clienteRepository.findAll();
        for (Cliente cliente : listaDeClientes) {
            KeySistema keySistema = keySistemaRepository.findByIdSistema(cliente.getChave());
            if (keySistema != null) {
                keySistema.setVersao(cliente.getVersao());
                keySistema.setUltimoAcesso(cliente.getUltimoAcesso());
                if (cliente.getValidade() != null) keySistema.setValidade(cliente.getValidade());
                if (cliente.getCertificado() != null) keySistema.setCertificado(cliente.getCertificado());
                if (cliente.getLiberacao().equals("")) keySistema.setPendente(0);
                keySistemaRepository.save(keySistema);
            }
        }
        List<Representante> listaDeRepresentantes = representanteRepository.findAll();
        for (Representante representante : listaDeRepresentantes) {
            Optional<Vendedor> optionalVendedor = vendedorRepository.findById(representante.getIdRepresentante());
            if (optionalVendedor.isPresent()) {
                Vendedor vendedor = optionalVendedor.get();
                String nome = representante.getNome() + " - " + representante.getCpf();
                vendedor.setNome(nome);
                vendedor.setComissao(BigDecimal.ZERO);
                vendedorRepository.save(vendedor);
            }
        }
    }


}
