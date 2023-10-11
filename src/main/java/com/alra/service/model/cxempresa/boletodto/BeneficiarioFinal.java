package com.alra.service.model.cxempresa.boletodto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeneficiarioFinal {
    @Column(name = "nome_benef_final", nullable = false)
    @JsonProperty("nome")
    private String nome = "Alra Sistemas";
    @Column(name = "cpf_cnpj_benef_final", nullable = false)
    @JsonProperty("cpfCnpj")
    private String cpfCnpj = "24330355000107";
    @Column(name = "tipo_pessoa_benef_final", nullable = false)
    @JsonProperty("tipoPessoa")
    private String tipoPessoa = "JURIDICA";
    @Column(name = "cep_benef_final", nullable = false)
    @JsonProperty("cep")
    private String cep = "12940640";
    @Column(name = "endereco_benef_final", nullable = false)
    @JsonProperty("endereco")
    private String endereco = "Rua José Bim";
    @Column(name = "bairro_benef_final", nullable = false)
    @JsonProperty("bairro")
    private String bairro = "Centro";
    @Column(name = "cidade_benef_final", nullable = false)
    @JsonProperty("cidade")
    private String cidade = "Atibaia";
    @Column(name = "uf_benef_final", nullable = false)
    @JsonProperty("uf")
    private String uf = "SP";
}
