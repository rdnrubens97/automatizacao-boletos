package com.alra.service.repository.cx230;

import com.alra.service.model.cx230.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendedorRepository extends JpaRepository<Vendedor, Integer> {
}
