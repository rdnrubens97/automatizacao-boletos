package com.alra.service.model.cxempresa.boletodto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Mora {
    @Column(name = "codigo_mora", nullable = false)
    @JsonProperty("codigoMora")
    private String codigoMora = "ISENTO";
    @Column(name = "data_mora", nullable = false)
    @JsonProperty("data")
    private String data = "";
    @Column(name = "taxa_mora", nullable = false)
    @JsonProperty("taxa")
    private BigDecimal taxa = BigDecimal.ZERO;
    @Column(name = "valor_mora", nullable = false)
    @JsonProperty("valor")
    private BigDecimal valor = BigDecimal.ZERO;
}
