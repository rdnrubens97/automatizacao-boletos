package com.alra.service.repository.cx230;

import com.alra.service.model.cx230.GeradorBoleto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface GeradorBoletoRepository extends JpaRepository<GeradorBoleto, String> {
    @Transactional
    @Modifying
    @Query(value = ComandosSqlCx230.ATUALIZAR_TBL_GERACAO_BOLETO, nativeQuery = true)
    void popularTblGeracaoBoleto();

    @Transactional
    @Modifying
    @Query(value = ComandosSqlCx230.ATUALIZAR_COLUNA_EMISSAOBOLETO_TBL_KEY_SISTEMA, nativeQuery = true)
    void atualizarColunaEmissaoBoletoTblKeySistema(
                                @Param("dataVencimento") Date dataVencimento,
                                @Param("seuNumero") String seuNumero);
}
