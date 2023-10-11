package com.alra.service.model.cxempresa.retornoconsultaboleto;

import com.alra.service.model.cxempresa.boletodto.Mensagem;
import com.alra.service.model.cxempresa.boletodto.Pagador;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Content {
    @JsonProperty("nomeBeneficiario")
    private String nomeBeneficiario;
    @JsonProperty("cnpjCpfBeneficiario")
    private String cnpjCpfBeneficiario;
    @JsonProperty("tipoPessoaBeneficiario")
    private String tipoPessoaBeneficiario;
    @JsonProperty("contaCorrente")
    private String contaCorrente;
    @JsonProperty("nossoNumero")
    private String nossoNumero;
    @JsonProperty("seuNumero")
    private String seuNumero;
    @JsonProperty("pagador")
    private Pagador pagador;
    @JsonProperty("motivoCancelamento")
    private String motivoCancelamento;
    @JsonProperty("situacao")
    private String situacao;
    @JsonProperty("dataHoraSituacao")
    private String dataHoraSituacao;
    @JsonProperty("dataVencimento")
    private String dataVencimento;
    @JsonProperty("valorNominal")
    private BigDecimal valorNominal;
    @JsonProperty("valorTotalRecebimento")
    private BigDecimal valorTotalRecebimento;
    @JsonProperty("dataEmissao")
    private String dataEmissao;
    @JsonProperty("dataLimite")
    private String dataLimite;
    @JsonProperty("codigoEspecie")
    private String codigoEspecie;
    @JsonProperty("codigoBarras")
    private String codigoBarras;
    @JsonProperty("linhaDigitavel")
    private String linhaDigitavel;
    @JsonProperty("origem")
    private String origem;
    @JsonProperty("mensagem")
    private Mensagem mensagem;
    @JsonProperty("desconto1")
    private DescontoConsulta desconto1;
    @JsonProperty("desconto2")
    private DescontoConsulta desconto2;
    @JsonProperty("desconto3")
    private DescontoConsulta desconto3;
    @JsonProperty("multa")
    private MultaConsulta multa;
    @JsonProperty("mora")
    private MoraConsulta mora;

    public Date getDataHoraSituacaoAsDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.parse(dataHoraSituacao);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
