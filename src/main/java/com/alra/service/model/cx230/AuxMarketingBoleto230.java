package com.alra.service.model.cx230;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "aux_marketing_boleto_cx230")
public class AuxMarketingBoleto230 {
    @Id
    @Column(name = "id_cliente")
    private String idCliente;
    @Column(name = "tel1")
    private String tel1;
    @Column(name = "tel2")
    private String tel2;
    @Column(name = "nome_cliente")
    private String nomeCliente;
    @Column(name = "cpf_cliente")
    private String cpfCliente;
    @Column(name = "id_sistema")
    private String idSistema;
}
