package com.alra.service.model.cx230;

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
@Table(name = "tbl_keysistema")
public class KeySistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key")
    private Integer key;
    @Column(name = "id_cliente")
    private String idCliente;
    @Column(name = "id_sistema")
    private String idSistema;
    @Column(name = "dia")
    private String dia;
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;
    @Column(name = "liberacao")
    private Date liberacao;
    @Column(name = "pendente")
    private Integer pendente;
    @Column(name = "versao")
    private String versao;
    @Column(name = "lib")
    private String lib;
    @Column(name = "ultimoacesso")
    private Date ultimoAcesso;
    @Column(name = "validade")
    private Date validade;
    @Column(name = "pc")
    private Integer pc;
    @Column(name = "premium")
    private Integer premium;
    @Column(name = "liberou")
    private Date liberou;
    @Column(name = "codmanual")
    private String codManual;
    @Column(name = "status")
    private Integer status;
    @Column(name = "id_vendedor")
    private Integer idVendedor;
    @Column(name = "comissao", precision = 10, scale = 2)
    private BigDecimal comissao;
    @Column(name = "pagouagora")
    private String pagouAgora;
    @Column(name = "valorpago", precision = 10, scale = 2)
    private BigDecimal valorPago;
    @Column(name = "obs")
    private String obs;
    @Column(name = "datacadastro")
    private Date dataCadastro;
    @Column(name = "dataalteracao")
    private Date dataAlteracao;
    @Column(name = "motivo")
    private Integer motivo;
    @Column(name = "marketing")
    private Integer marketing;
    @Column(name = "tempoempresa")
    private Integer tempoEmpresa;
    @Column(name = "segmento")
    private Integer segmento;
    @Column(name = "segmentoobs")
    private String segmentoObs;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "email")
    private String email;
    @Column(name = "nome")
    private String nome;
    @Column(name = "mensagem")
    private String mensagem;
    @Column(name = "uf")
    private String uf;
    @Column(name = "valorpg", precision = 10, scale = 2)
    private BigDecimal valorPg;
    @Column(name = "periodo")
    private Integer periodo;
    @Column(name = "meses")
    private Integer meses;
    @Column(name = "emissaoboleto")
    private Date emissaoBoleto;
    @Column(name = "link")
    private String link;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "certificado")
    private Date certificado;
    @Column(name = "enviar")
    private Integer enviar;
    @Column(name = "marketingcertificado")
    private Date marketingCertificado;
}
