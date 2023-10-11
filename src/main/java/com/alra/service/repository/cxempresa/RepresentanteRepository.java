package com.alra.service.repository.cxempresa;

import com.alra.service.model.cxempresa.representante.Representante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentanteRepository extends JpaRepository<Representante, Integer> {
}
