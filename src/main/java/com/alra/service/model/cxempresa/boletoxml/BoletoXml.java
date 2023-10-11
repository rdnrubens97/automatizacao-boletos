package com.alra.service.model.cxempresa.boletoxml;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aux_boleto_xml")
public class BoletoXml {
    @Id
    @Column(name = "seu_numero", updatable = false, unique = true, nullable = false)
    private String seuNumero;
    @Column(name = "nome_pagador", nullable = false)
    private String nome;
    @Column(name = "cpf_cnpj", nullable = false)
    private String cpfCnpj;
    @Column(name = "xml", nullable = false, columnDefinition = "TEXT")
    private String xml;
    @Column(name = "enviado_cliente", nullable = false, columnDefinition = "false")
    private boolean enviadoCliente;
}
