package com.alra.service.model.cx230;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aux_marketing")
public class Marketing {
    @Id
    @Column(name = "id_sistema")
    private String idSistema;
    @Column(name = "id_cliente")
    private String idCliente;
    @Column(name = "nome_cliente")
    private String nomeCliente;
    @Column(name = "cpf_cliente")
    private String cpfCliente;
    @Column(name = "tel1")
    private String tel1;
    @Column(name = "tel2")
    private String tel2;
    @Column(name = "certificado")
    private Date certificado;
}
