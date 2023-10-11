package com.alra.service.model.cx230;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inf_vendedor")
public class Vendedor {
    @Id
    @Column(name = "id_vendedor")
    private Integer idVendedor;
    @Column(name = "nome")
    private String nome;
    @Column(name = "comissao")
    private BigDecimal comissao;
}
