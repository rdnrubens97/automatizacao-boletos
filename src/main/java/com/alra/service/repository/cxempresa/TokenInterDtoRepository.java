package com.alra.service.repository.cxempresa;

import com.alra.service.model.cxempresa.token.TokenInterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenInterDtoRepository extends JpaRepository<TokenInterDto, String> {
}
