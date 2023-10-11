package com.alra.service.model.cxempresa.cliente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_cliente", uniqueConstraints = @UniqueConstraint(columnNames = "chave"))
public class Cliente {
    @Id
    @Column(name = "id_cliente")
    private Long idCliente;
    @Column(name = "chave", nullable = false, unique = true)
    private String chave;
    @Column(name = "cadastro", nullable = false)
    private Date cadastro;
    @Column(name = "segmento", nullable = false)
    private Integer segmento;
    @Column(name = "ultimoacesso", nullable = false)
    private Date ultimoAcesso;
    @Column(name = "marketing", nullable = false)
    private Integer marketing;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "email")
    private String email;
    @Column(name = "nome")
    private String nome;
    @Column(name = "telefone2")
    private String telefone2;
    @Column(name = "liberacao", nullable = false)
    private String liberacao;
    @Column(name = "validade")
    private Date validade;
    @Column(name = "segmentoobs", nullable = false)
    private String segmentoObs;
    @Column(name = "uf")
    private String uf;
    @Column(name = "tempoempresa")
    private Integer tempoEmpresa;
    @Column(name = "nota")
    private Integer nota;
    @Column(name = "status", nullable = false)
    private Integer status;
    @Column(name = "pc", nullable = false, columnDefinition = "default 1")
    private Integer pc;
    @Column(name = "periodo")
    private Integer periodo;
    @Column(name = "mensagem")
    private String mensagem;
    @Column(name = "versao", nullable = false, columnDefinition = "default 'servidor'")
    private String versao;
    @Column(name = "id_vendedor")
    private Integer idVendedor;
    @Column(name = "comissao", precision = 10, scale = 2, columnDefinition = "default 0.00")
    private BigDecimal comissao;
    @Column(name = "fator", nullable = false)
    private Integer fator;
    @Column(name = "fatorpg", columnDefinition = "default 0")
    private Integer fatorPg;
    @Column(name = "ultimopg")
    private Date ultimoPg;
    @Column(name = "certificado")
    private Date certificado;
    @Column(name = "backup")
    private Date backup;
}
