package com.alra.service.model.cx230;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tbl_estoque")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estoque")
    private Integer idEstoque;
    @Column(name = "id_notafiscal")
    private Integer idNotaFiscal;
    @Column(name = "id_produto")
    private Integer idProduto;
    @Column(name = "id_pedido")
    private Integer idPedido;
    @Column(name = "id_func")
    private Integer idFuncao;
    @Column(name = "funcao")
    private Integer funcao;
    @Column(name = "quantidade")
    private BigDecimal quantidade;
    @Column(name = "preco")
    private BigDecimal preco;
    @Column(name = "precototal")
    private BigDecimal precoTotal;


}
