package com.alra.service.repository.cxempresa;

import com.alra.service.model.cxempresa.token.TokenInter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenInterRepository extends JpaRepository<TokenInter, String> {

}
