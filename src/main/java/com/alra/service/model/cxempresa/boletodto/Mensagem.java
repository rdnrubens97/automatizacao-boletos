package com.alra.service.model.cxempresa.boletodto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Mensagem {
    @Column(name = "linha1", nullable = false)
    @JsonProperty("linha1")
    private String linha1;
    @Column(name = "linha2", nullable = false)
    @JsonProperty("linha2")
    private String linha2;
    @Column(name = "linha3", nullable = false)
    @JsonProperty("linha3")
    private String linha3;
    @Column(name = "linha4", nullable = false)
    @JsonProperty("linha4")
    private String linha4;
    @Column(name = "linha5", nullable = false)
    @JsonProperty("linha5")
    private String linha5;

    public Mensagem(String mensagem) {
        formatMensagem(mensagem);
    }

    // Método para formatar a mensagem em linhas de 77 caracteres
    public void formatMensagem(String mensagem) {
        String[] linhas = mensagem.split("(?<=\\G.{77})");
        linha1 = (linhas.length > 0) ? linhas[0] : "";
        linha2 = (linhas.length > 1) ? linhas[1] : "";
        linha3 = (linhas.length > 2) ? linhas[2] : "";
        linha4 = (linhas.length > 3) ? linhas[3] : "";
        linha5 = (linhas.length > 4) ? linhas[4] : "";
    }

}
