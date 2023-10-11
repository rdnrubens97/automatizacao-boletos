package com.alra.service.repository.cxempresa;

import com.alra.service.model.cxempresa.boletodto.BoletoDto;
import com.alra.service.model.cxempresa.retornoconsultaboleto.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Repository
public interface BoletoDtoRepository extends JpaRepository<BoletoDto, String> {

    @Query(value = ComandosSqlCxempresa.RETORNAR_ID_SISTEMA_COM_NOSSO_NUMERO, nativeQuery = true)
    String retornarIdSistemaComNossoNumero(@Param("nossoNumero") String nossoNumero);

    @Modifying
    @Transactional
    @Query(value = ComandosSqlCxempresa.INSERE_LIBERACAO_TBL_CLIENTE, nativeQuery = true)
    void insereLiberacaoTblCliente(
            @Param("idSistema") String idSistema,
            @Param("codigoAutomatico") String codigoAutomatico
    );



}
