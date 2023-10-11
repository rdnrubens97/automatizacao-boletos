package com.alra.service.model.cxempresa.retornoconsultaboleto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class MoraConsulta {
    @JsonProperty("codigo")
    private String codigo;
    @JsonProperty("data")
    private String data;
    @JsonProperty("taxa")
    private BigDecimal taxa;
    @JsonProperty("valor")
    private BigDecimal valor;
}
