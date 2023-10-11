package com.alra.service.model.cxempresa.log;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tbl_log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Integer idLog;
    @Column(name = "id_sistema")
    private String idSistema;
    @Column(name = "dia_hora")
    private Date diaHora;
    @Column(name = "mensagem", columnDefinition = "TEXT")
    private String mensagem;
    @Column(name = "tipo")
    private String tipo;

}
