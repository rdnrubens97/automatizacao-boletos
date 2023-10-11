package com.alra.service.model.cxempresa.boletodto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Multa {
    @Column(name = "codigo_multa", nullable = false)
    @JsonProperty("codigoMulta")
    private String codigoMulta;
    @Column(name = "data_multa", nullable = false)
    @JsonProperty("data")
    private String data;
    @Column(name = "taxa_multa", nullable = false)
    @JsonProperty("taxa")
    private BigDecimal taxa;
    @Column(name = "valor_multa", nullable = false)
    @JsonProperty("valor")
    private BigDecimal valor;

    public Multa(Date vencimento) {
        this.codigoMulta = "PERCENTUAL";
        this.data = calcularData(vencimento);
        this.taxa = new BigDecimal(2);
        this.valor = BigDecimal.ZERO;
    }

    private String calcularData(Date vencimento) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(vencimento);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dataMulta = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataMultaFormatada = dateFormat.format(dataMulta);
        return dataMultaFormatada;
    }

}
