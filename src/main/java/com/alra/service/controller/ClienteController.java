package com.alra.service.controller;

import com.alra.service.model.cx230.VendedorSistema;
import com.alra.service.model.cxempresa.cliente.Cliente;
import com.alra.service.service.cx230.VendedorSistemaService;
import com.alra.service.service.cxempresa.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;
    @Autowired
    VendedorSistemaService vendedorSistemaService;

    @GetMapping(value = "listar")
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok().body(clientes);
    }

    @GetMapping(value = "imprimindo")
    public ResponseEntity<List<VendedorSistema>> listarVendedorSistema() {
        List<VendedorSistema> clientes = vendedorSistemaService.listarVendedorSistema();
        return ResponseEntity.ok().body(clientes);
    }

}

