package com.alra.service.model.cxempresa.representante;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_representante")
public class Representante {
    @Id
    @Column(name = "id_representante")
    private Integer idRepresentante;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "rua")
    private String rua;
    @Column(name = "numero")
    private String numero;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cep")
    private String cep;
    @Column(name = "uf")
    private String uf;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "banco")
    private String banco;
    @Column(name = "agencia")
    private String agencia;
    @Column(name = "conta")
    private String conta;
    @Column(name = "pix")
    private String pix;
    @Column(name = "telefone1")
    private String telefone1;
    @Column(name = "telefone2")
    private String telefone2;
    @Column(name = "login")
    private String login;
    @Column(name = "senha")
    private String senha;
    @Column(name = "data")
    private String data;
    @Column(name = "alteracao")
    private String alteracao;
    @Column(name = "saldo")
    private String saldo;
    @Column(name = "email")
    private String email;
    @Column(name = "ultimopg")
    private String ultimopg;
    @Column(name = "cupom")
    private String cupom;
    @Column(name = "desc")
    private BigDecimal desc;

}
