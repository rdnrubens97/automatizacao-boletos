package com.alra.service.model.cx230;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "aux_marketing_envio_chave")
public class AuxMarketingEnvioChave {
    @Id
    @Column(name = "id_sistema")
    private String idSistema;
    @Column(name = "codManual")
    private String codManual;
    @Column(name = "tel1")
    private String tel1;
}
