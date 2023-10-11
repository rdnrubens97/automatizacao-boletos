package com.alra.service.repository.cxempresa;

import com.alra.service.model.cxempresa.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByChave(String chave);
}
