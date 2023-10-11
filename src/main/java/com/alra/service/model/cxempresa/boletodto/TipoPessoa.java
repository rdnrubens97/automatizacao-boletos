package com.alra.service.model.cxempresa.boletodto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public enum TipoPessoa {
    @Column
    FISICA,
    JURIDICA
}
