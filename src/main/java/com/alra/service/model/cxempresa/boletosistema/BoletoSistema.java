package com.alra.service.model.cxempresa.boletosistema;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_boleto")
public class BoletoSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleto")
    private Integer idBoleto;
    @Column(name = "id_sistema", nullable = false)
    private String idSistema;
    @Column(name = "dataemissao")
    private Date dataEmissao;
    @Column(name = "link")
    private String link;
    @Column(name = "status", nullable = false)
    private Integer status;
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "vencimento")
    private Date vencimento;
    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;
    @Column(name = "pagostatus", nullable = false)
    private Integer pagamentoStatus;
    @Column(name = "datapagamento")
    private Date dataPagamento;
    @Column(name = "valorpago")
    private BigDecimal valorPago;
    @Column(name = "taxa")
    private BigDecimal taxa;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "tel")
    private String telefone;
    @Column(name = "liberacao")
    private Integer liberacao;
    @Column(name = "qrcode")
    private String qrCode;
    @Column(name = "enviowhatsapp")
    private Date envioWhatsApp;
    @Column(name = "email")
    private String email;
    @Column(name = "emailstatus", nullable = false)
    private Integer emailStatus;

    public String getLink() {
        return this.link;
    }

    public void setLink(){
        String url = "https://www.alrasistemas.com.br/boletos/";
        this.link = url + this.codigo + ".pdf";
    }

}
