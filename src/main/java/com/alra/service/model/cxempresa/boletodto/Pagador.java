package com.alra.service.model.cxempresa.boletodto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pagador {
    @Column(name = "cpf_cnpj", nullable = false)
    @JsonProperty("cpfCnpj")
    private String cpfCnpj; // cpfcliente
    @Column(name = "tipo_pessoa_pagador", nullable = false)
    @JsonProperty("tipoPessoa")
    private String tipoPessoa = "FISICA"; // tipo_pessoa
    @Column(name = "nome_pagador", nullable = false)
    @JsonProperty("nome")
    private String nome; // nomecliente
    @Column(name = "endereco_pagador", nullable = false)
    @JsonProperty("endereco")
    private String endereco; // rua
    @Column(name = "numero_pagador", nullable = false)
    @JsonProperty("numero")
    private String numero; // numero
    @Column(name = "complemento_pagador", nullable = false)
    @JsonProperty("complemento")
    private String complemento; // complemento
    @Column(name = "bairro_pagador", nullable = false)
    @JsonProperty("bairro")
    private String bairro; // bairro
    @Column(name = "cidade_pagador", nullable = false)
    @JsonProperty("cidade")
    private String cidade; // cidade
    @Column(name = "uf_pagador", nullable = false)
    @JsonProperty("uf")
    private String uf;  // uf
    @Column(name = "cep_pagador", nullable = false)
    @JsonProperty("cep")
    private String cep; // cep
    @Column(name = "email_pagador", nullable = false)
    @JsonProperty("email")
    private String email; // email
    @Column(name = "ddd_pagador", nullable = false)
    @JsonProperty("ddd")
    private String ddd = "";
    @Column(name = "telefone_pagador", nullable = false)
    @JsonProperty("telefone")
    private String telefone; // tel1

    public void setComplemento(String complemento) {
        this.complemento = complemento != null ? complemento : "";
    }






}
