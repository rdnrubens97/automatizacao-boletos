package com.alra.service.model.cx230;

import com.alra.service.model.cxempresa.boletodto.BoletoDto;
import  com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aux_geracao_boleto")
public class GeradorBoleto {
    @Column(name = "meses")
    private Integer meses;
    @Column(name = "dia")
    private String dia;
    @Temporal(TemporalType.DATE)
    @Column(name = "emissaoboleto")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date emissaoboleto;
    @Column(name = "count")
    private BigInteger count;
    @Column(name = "rua")
    private String rua;
    @Column(name = "numero")
    private String numero;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "cep")
    private String cep;
    @Column(name = "uf")
    private String uf;
    @Column(name = "tel1")
    private String tel1;
    @Column(name = "email")
    private String email;
    @Column(name = "nomecliente")
    private String nomecliente;
    @Column(name = "cpfcliente")
    private String cpfcliente;
    @Column(name = "id_sistema")
    private String idSistema;
    @Column(name = "validade")
    private Date validade;
    @Column(name = "versao")
    private String versao;
    @Column(name = "ultimoacesso")
    private Date ultimoacesso;
    @Column(name = "pendente")
    private Integer pendente;
    @Column(name = "liberou")
    private Date liberou;
    @Column(name = "premium")
    private String premium;
    @Column(name = "pc")
    private Integer pc;
    @Column(name = "valor_sistema")
    private BigDecimal valorSistema;
    @Column(name = "codmanual")
    private String codmanual;
    @Id
    @Column(name = "id_cliente")
    private String idCliente;
    @Column(name = "pagouagora")
    private String pagouagora;
    @Column(name = "valorpago")
    private String valorpago;
    @Column(name = "key")
    private Integer key;
    @Column(name = "tempoempresa")
    private Integer tempoempresa;
    @Column(name = "nome_vendedor")
    private String nomeVendedor;
    @Column(name = "id_vendedor")
    private String idVendedor;
    @Column(name = "comissao")
    private String comissao;
    @Column(name = "descricao")
    private String descricao;
    @Temporal(TemporalType.DATE)
    @Column(name = "vencimento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date vencimento;
    @Column(name = "tipo_pessoa")
    private String tipoPessoa;

    public void atualizarVencimento() {
        Date agora = new Date();
        Date dataVencimento = this.vencimento;

        while (dataVencimento.before(agora)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dataVencimento);
            calendar.add(Calendar.MONTH, 1);
            dataVencimento = calendar.getTime();
        }

        this.vencimento = dataVencimento;
    }


}
