package com.alra.service.model.cxempresa.boletoregistrado;

import com.alra.service.model.cxempresa.boletodto.BoletoDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Data
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aux_boleto_registrado")
@EqualsAndHashCode(of = "nossoNumero")
public class BoletoRegistrado {
    @Id
    @Column(name = "nosso_numero", updatable = false)
    private String nossoNumero;
    @Column(name = "codigo_barras", updatable = false, unique = true)
    private String codigoBarras;
    @Column(name = "linha_digitavel", updatable = false)
    private String linhaDigitavel;
    @Column(name = "seu_numero", updatable = false)
    private String seuNumero;
    @Column(name = "emitido_em", updatable = false)
    private Date emitidoEm;
    @Column(name = "valor_nominal", nullable = false)
    private BigDecimal valorNominal;
    @Column(name = "data_vencimento", nullable = false)
    private Date dataVencimento;
    @Column(name = "num_dias_agenda", nullable = false)
    private Integer numDiasAgenda;
    @Column(name = "cpf_cnpj_pagador", nullable = false)
    private String cpfCnpjPagador;
    @Column(name = "tipo_pessoa_pagador", nullable = false)
    private String tipoPessoaPagador;
    @Column(name = "nome_pagador", nullable = false)
    private String nomePagador;
    @Column(name = "endereco_pagador", nullable = false)
    private String enderecoPagador;
    @Column(name = "numero_pagador", nullable = false)
    private String numeroPagador;
    @Column(name = "complemento_pagador", nullable = false)
    private String complementoPagador;
    @Column(name = "bairro_pagador", nullable = false)
    private String bairroPagador;
    @Column(name = "cidade_pagador", nullable = false)
    private String cidadePagador;
    @Column(name = "uf_pagador", nullable = false)
    private String ufPagador;
    @Column(name = "cep_pagador", nullable = false)
    private String cepPagador;
    @Column(name = "email_pagador", nullable = false)
    private String emailPagador;
    @Column(name = "ddd_pagador", nullable = false)
    private String dddPagador;
    @Column(name = "telefone_pagador", nullable = false)
    private String telefonePagador;
    @Column(name = "mensagem_linha1", nullable = false)
    private String linha1;
    @Column(name = "mensagem_linha2", nullable = false)
    private String linha2;
    @Column(name = "mensagem_linha3", nullable = false)
    private String linha3;
    @Column(name = "mensagem_linha4", nullable = false)
    private String linha4;
    @Column(name = "mensagem_linha5", nullable = false)
    private String linha5;
    @Column(name = "codigo_desconto_1", nullable = false)
    private String codigoDesconto1;
    @Column(name = "data_desconto_1", nullable = false)
    private String dataDesconto1;
    @Column(name = "taxa_desconto_1", nullable = false)
    private BigDecimal taxaDesconto1;
    @Column(name = "valor_desconto_1", nullable = false)
    private BigDecimal valorDesconto1;
    @Column(name = "codigo_desconto_2", nullable = false)
    private String codigoDesconto2;
    @Column(name = "data_desconto_2", nullable = false)
    private String dataDesconto2;
    @Column(name = "taxa_desconto_2", nullable = false)
    private BigDecimal taxaDesconto2;
    @Column(name = "valor_desconto_2", nullable = false)
    private BigDecimal valorDesconto2;
    @Column(name = "codigo_desconto_3", nullable = false)
    private String codigoDesconto3;
    @Column(name = "data_desconto_3", nullable = false)
    private String dataDesconto3;
    @Column(name = "taxa_desconto_3", nullable = false)
    private BigDecimal taxaDesconto3;
    @Column(name = "valor_desconto_3", nullable = false)
    private BigDecimal valorDesconto3;
    @Column(name = "codigo_multa", nullable = false)
    private String codigoMulta;
    @Column(name = "data_multa", nullable = false)
    private String dataMulta;
    @Column(name = "taxa_multa", nullable = false)
    private BigDecimal taxaMulta;
    @Column(name = "valor_multa", nullable = false)
    private BigDecimal valorMulta;
    @Column(name = "codigo_mora", nullable = false)
    private String codigoMora;
    @Column(name = "data_mora", nullable = false)
    private String dataMora;
    @Column(name = "taxa_mora", nullable = false)
    private BigDecimal taxaMora;
    @Column(name = "valor_mora", nullable = false)
    private BigDecimal valorMora;
    @Column(name = "nome_benef_final", nullable = false)
    private String nomeBeneficiarioFinal;
    @Column(name = "cpf_cnpj_benef_final", nullable = false)
    private String cpfCnpjBeneficiarioFinal;
    @Column(name = "tipo_pessoa_benef_final", nullable = false)
    private String tipoPessoaBeneficiarioFinal;
    @Column(name = "cep_benef_final", nullable = false)
    private String cepBeneficiarioFinal;
    @Column(name = "endereco_benef_final", nullable = false)
    private String enderecoBeneficiarioFinal;
    @Column(name = "bairro_benef_final", nullable = false)
    private String bairroBeneficiarioFinal;
    @Column(name = "cidade_benef_final", nullable = false)
    private String cidadeBeneficiarioFinal;
    @Column(name = "uf_benef_final", nullable = false)
    private String ufBeneficiarioFinal;
    @Column(name = "gerado_xml", nullable = false)
    private boolean geradoXml;



    public void atualizarBoletoRegistradoComBoleto(BoletoDto boletoDto) {
        LocalDateTime now = LocalDateTime.now();
        ZoneId zone = ZoneId.of("America/Sao_Paulo");
        Instant instant = now.atZone(zone).toInstant();
        this.emitidoEm = Date.from(instant);
        this.valorNominal = boletoDto.getValorNominal();
        this.dataVencimento = boletoDto.getDataVencimento();
        this.numDiasAgenda = boletoDto.getNumDiasAgenda();
        this.cpfCnpjPagador = boletoDto.getPagador().getCpfCnpj();
        this.tipoPessoaPagador = boletoDto.getPagador().getTipoPessoa();
        this.nomePagador = boletoDto.getPagador().getNome();
        this.enderecoPagador = boletoDto.getPagador().getEndereco();
        this.numeroPagador = boletoDto.getPagador().getNumero();
        this.complementoPagador = boletoDto.getPagador().getComplemento();
        this.bairroPagador = boletoDto.getPagador().getBairro();
        this.cidadePagador = boletoDto.getPagador().getCidade();
        this.ufPagador = boletoDto.getPagador().getUf();
        this.cepPagador = boletoDto.getPagador().getCep();
        this.emailPagador = boletoDto.getPagador().getEmail();
        this.dddPagador = boletoDto.getPagador().getDdd();
        this.telefonePagador = boletoDto.getPagador().getTelefone();
        this.linha1 = boletoDto.getMensagem().getLinha1();
        this.linha2 = boletoDto.getMensagem().getLinha2();
        this.linha3 = boletoDto.getMensagem().getLinha3();
        this.linha4 = boletoDto.getMensagem().getLinha4();
        this.linha5 = boletoDto.getMensagem().getLinha5();
        this.codigoDesconto1 = boletoDto.getDesconto1().getCodigoDesconto();
        this.dataDesconto1 = boletoDto.getDesconto1().getData();
        this.taxaDesconto1 = boletoDto.getDesconto1().getTaxa();
        this.valorDesconto1 = boletoDto.getDesconto1().getValor();
        this.codigoDesconto2 = boletoDto.getDesconto2().getCodigoDesconto();
        this.dataDesconto2 = boletoDto.getDesconto2().getData();
        this.taxaDesconto2 = boletoDto.getDesconto2().getTaxa();
        this.valorDesconto2 = boletoDto.getDesconto2().getValor();
        this.codigoDesconto3 = boletoDto.getDesconto3().getCodigoDesconto();
        this.dataDesconto3 = boletoDto.getDesconto3().getData();
        this.taxaDesconto3 = boletoDto.getDesconto3().getTaxa();
        this.valorDesconto3 = boletoDto.getDesconto3().getValor();
        this.codigoMulta = boletoDto.getMulta().getCodigoMulta();
        this.dataMulta = boletoDto.getMulta().getData();
        this.taxaMulta = boletoDto.getMulta().getTaxa();
        this.valorMulta = boletoDto.getMulta().getValor();
        this.codigoMora = boletoDto.getMora().getCodigoMora();
        this.dataMora = boletoDto.getMora().getData();
        this.taxaMora = boletoDto.getMora().getTaxa();
        this.valorMora = boletoDto.getMora().getValor();
        this.nomeBeneficiarioFinal = boletoDto.getBeneficiarioFinal().getNome();
        this.cpfCnpjBeneficiarioFinal = boletoDto.getBeneficiarioFinal().getCpfCnpj();
        this.tipoPessoaBeneficiarioFinal = boletoDto.getBeneficiarioFinal().getTipoPessoa();
        this.cepBeneficiarioFinal = boletoDto.getBeneficiarioFinal().getCep();
        this.enderecoBeneficiarioFinal = boletoDto.getBeneficiarioFinal().getEndereco();
        this.bairroBeneficiarioFinal = boletoDto.getBeneficiarioFinal().getBairro();
        this.cidadeBeneficiarioFinal = boletoDto.getBeneficiarioFinal().getCidade();
        this.ufBeneficiarioFinal = boletoDto.getBeneficiarioFinal().getUf();
        this.geradoXml = false;
    }


}
