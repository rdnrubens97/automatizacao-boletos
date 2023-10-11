package com.alra.service.model.cx230;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aux_liberador_de_sistema")
public class LiberadorDeSistema {
    @Id
    @Column(name = "id_sistema")
    private String idSistema;
    @Column(name = "id_cliente")
    private String idCliente;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "emissao_boleto")
    private String emissaoBoleto;
    @Column(name = "meses")
    private String meses;
    @Column(name = "tel1")
    private String tel1;
    @Column(name = "tel2")
    private String tel2;
    @Column(name = "nome_cliente")
    private String nomeCliente;
    @Column(name = "cpf_cliente")
    private String cpfCliente;
    @Column(name = "validade")
    private String validade;
    @Column(name = "versao")
    private String versao;
    @Column(name = "ultimo_acesso")
    private Date ultimoAcesso;
    @Column(name = "pendente")
    private Integer pendente;
    @Column(name = "liberou")
    private Date liberou;
    @Column(name = "dia")
    private String dia;
    @Column(name = "produto")
    private Integer produto;
    @Column(name = "pc")
    private Integer pc;
    @Column(name = "valor")
    private BigDecimal valor;
    @Column(name = "marketing")
    private Integer marketing;
    @Column(name = "cod_manual")
    private String codManual;
    @Column(name = "pagou_agora")
    private String pagouAgora;
    @Column(name = "valor_pago")
    private String valorPago;
    @Column(name = "key")
    private Integer key;
    @Column(name = "tempo_empresa")
    private Integer tempoEmpresa;
    @Column(name = "segname")
    private Integer segname;
    @Column(name = "segmento_obs")
    private String segmentoObs;
    @Column(name = "nome_vendedor")
    private String nomeVendedor;
    @Column(name = "id_vendedor")
    private String idVendedor;
    @Column(name = "comissao")
    private BigDecimal comissao;
    @Column(name = "mes_ano")
    private String mesAno;
    @Column(name = "data")
    private String data;
    @Column(name = "codigo_manual")
    private String codigoManual;
    @Column(name = "codigo_automatico")
    private String codigoAutomatico;

    public void configurarCampos() {
        setMesAno();
        setData();
        setCodigoManual();
        setCodigoAutomatico();
    }

    private String gerarCodigoManual(String versao, String minhaVersao) {
        System.out.println("versao: " + versao);
        System.out.println("minhaVersao: " + minhaVersao);
        int dia = Integer.parseInt(versao.substring(0, 2));
        int mes = Integer.parseInt(versao.substring(2, 4));
        int ano = Integer.parseInt(versao.substring(4, 8));
        int codMeu = Integer.parseInt(minhaVersao.substring(8));

        dia = dia * 1273187 + 35;
        mes = mes * 27611342 - 131021;
        ano = (ano - 1985) * 23 + 37102;

        int resposta = dia + mes + ano + codMeu;
        return (String) versao + resposta;
    }

    private String gerarCodigoAutomatico(String versao) {
        System.out.println("versao: " + versao);
        int dia = Integer.parseInt(versao.substring(0, 2));
        int mes = Integer.parseInt(versao.substring(2, 4));
        int ano = Integer.parseInt(versao.substring(4, 8));

        dia = dia * dia * 3;
        mes = mes * dia;
        ano = (ano - 1985) * 17;

        int resposta = dia + mes + ano + 14875154;
        return (String) versao + resposta;
    }

    private void setMesAno() {
        int mesAtual = LocalDate.now().getMonthValue();
        int proximoMes = (mesAtual % 12) + 1;
        int anoAtual = LocalDate.now().getYear();
        this.mesAno = String.format("%02d%04d", proximoMes, anoAtual);
    }

    private void setData() {
        this.data = dia + mesAno;
    }

    private void setCodigoManual() {
        this.codigoManual = gerarCodigoManual(this.data, this.idSistema);
    }

    private void setCodigoAutomatico() {
        this.codigoAutomatico = gerarCodigoAutomatico(this.data);
    }

}
