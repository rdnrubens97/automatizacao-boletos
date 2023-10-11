package com.alra.service.model.cxempresa.boletodto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aux_boleto_dto")
@EqualsAndHashCode(of = "seuNumero")
public class BoletoDto {
    @Id
    @Column(name = "seu_numero", updatable = false, unique = true, nullable = false)
    @JsonProperty("seuNumero")
    private String seuNumero; // id_sistema
    @Column(name = "valor_nominal", nullable = false)
    @JsonProperty("valorNominal")
    private BigDecimal valorNominal; // valor_sistema
    @Column(name = "data_vencimento", nullable = false)
    @JsonProperty("dataVencimento")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "America/Sao_Paulo")
    private Date dataVencimento; // vencimento
    @Column(name = "num_dias_agenda", nullable = false, columnDefinition = "integer default 60")
    @JsonProperty("numDiasAgenda")
    private Integer numDiasAgenda; // Número de dias corridos após o vencimento para o cancelamento efetivo automático do boleto. (de 0 até 60)
    @Column(name = "pagador", nullable = false)
    @JsonProperty("pagador")
    private Pagador pagador;
    @Column(name = "mensagem", nullable = false)
    @JsonProperty("mensagem")
    private Mensagem mensagem;
    @Column(name = "desconto1", nullable = false)
    @JsonProperty("desconto1")
    private Desconto1 desconto1;
    @Column(name = "desconto2", nullable = false)
    @JsonProperty("desconto2")
    private Desconto2 desconto2;
    @Column(name = "desconto3", nullable = false)
    @JsonProperty("desconto3")
    private Desconto3 desconto3;
    @Column(name = "multa", nullable = false)
    @JsonProperty("multa")
    private Multa multa;
    @Column(name = "mora", nullable = false)
    @JsonProperty("mora")
    private Mora mora;
    @Column(name = "beneficiario_final", nullable = false)
    @JsonProperty("beneficiarioFinal")
    private BeneficiarioFinal beneficiarioFinal;


    public void setMensagem(String mensagem) {
        if (this.mensagem == null) {
            this.mensagem = new Mensagem();
        }
        this.mensagem.formatMensagem(mensagem);
    }

    public void setMulta(Date vencimento) {
        Multa multa = new Multa(vencimento);
        this.multa = multa;
    }


    @Override
    public String toString() {
        return "BoletoDto: " +
                "\n    seuNumero='" + seuNumero +
                "\n    valorNominal=" + valorNominal +
                "\n    dataVencimento=" + dataVencimento +
                "\n    numDiasAgenda=" + numDiasAgenda +
                "\n    pagador=" + pagador +
                "\n    mensagem=" + mensagem +
                "\n    desconto1=" + desconto1 +
                "\n    desconto2=" + desconto2 +
                "\n    desconto3=" + desconto3 +
                "\n    multa=" + multa +
                "\n    mora=" + mora +
                "\n    beneficiarioFinal=" + beneficiarioFinal;
    }
}
