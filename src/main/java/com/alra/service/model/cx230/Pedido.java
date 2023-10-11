package com.alra.service.model.cx230;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer idPedido;
    @Column(name = "status")
    private Integer status;
    @Column(name = "idcliente")
    private Integer idCliente;
    @Column(name = "id_clientepf")
    private Integer idClientePf;
    @Column(name = "id_clientepj")
    private Integer idClientePj;
    @Column(name = "id_funcionario")
    private Integer idFuncionario;
    @Column(name = "acrescimo")
    private BigDecimal acrescimo;
    @Column(name = "desconto")
    private BigDecimal desconto;
    @Column(name = "valorliq")
    private BigDecimal valorLiquido;

}
