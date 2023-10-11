package com.alra.service.model.cx230;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inf_vendedorsistema")
public class VendedorSistema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vendedorsistema")
    private Integer idVendedorSistema;
    @Column(name = "id_vendedor")
    private Integer idVendedor;
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;
    @Column(name = "datahora")
    private Date dataHora;
    @Column(name = "status")
    private Integer status;
    @Column(name = "chave")
    private String chave;
    @Column(name = "empresa")
    private String empresa;


}
