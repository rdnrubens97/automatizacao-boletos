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
public class Desconto2 {
    @Column(name = "codigo_desconto_2", nullable = false)
    @JsonProperty("codigoDesconto")
    private String codigoDesconto = "NAOTEMDESCONTO";
    @Column(name = "data_desconto_2", nullable = false)
    @JsonProperty("data")
    private String data = "";
    @Column(name = "taxa_desconto_2", nullable = false)
    @JsonProperty("taxa")
    private BigDecimal taxa = BigDecimal.ZERO;
    @Column(name = "valor_desconto_2", nullable = false)
    @JsonProperty("valor")
    private BigDecimal valor = BigDecimal.ZERO;
}
