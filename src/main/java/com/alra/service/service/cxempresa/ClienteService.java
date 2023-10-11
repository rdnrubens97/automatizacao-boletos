package com.alra.service.service.cxempresa;

import com.alra.service.model.cxempresa.cliente.Cliente;
import com.alra.service.repository.cxempresa.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        List<Cliente> listaDeClientes = clienteRepository.findAll();
        return listaDeClientes;
    }


}
