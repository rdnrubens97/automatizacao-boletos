package com.alra.service.repository.cx230;

import com.alra.service.model.cx230.LiberadorDeSistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LiberadorDeSistemaRepository extends JpaRepository<LiberadorDeSistema, String> {

    @Transactional
    @Modifying
    @Query(value = ComandosSqlCx230.POPULAR_TBL_LIBERADOR_DE_SISTEMA, nativeQuery = true)
    void popularTblLiberadorDeSistema(@Param("idSistemaPrincipal") String idSistemaPrincipal);

    default void atualizarParametros(String idSistema) {
        LiberadorDeSistema liberadorDeSistemaBanco = findById(idSistema).orElse(null);
        liberadorDeSistemaBanco.configurarCampos();
        save(liberadorDeSistemaBanco);
    }

    @Transactional
    @Modifying
    @Query(value = ComandosSqlCx230.ATUALIZAR_TABELA_KEY_SISTEMA_EM_LIBERACAO, nativeQuery = true)
    void atualizarTabelaKeySistemaEmLiberacao(
            @Param("idSistema") String idSistema,
            @Param("codigoManual") String codigoManual
    );

    @Transactional
    @Modifying
    @Query("UPDATE LiberadorDeSistema l SET l.valorPago = :valorPago WHERE l.idSistema = :idSistema")
    void atualizarLiberadorSistemaComContent(
            @Param("idSistema") String idSistema,
            @Param("valorPago") String valorPago
    );

    @Transactional
    @Modifying
    @Query(value = ComandosSqlCx230.ATUALIZAR_DATA_AUX_LIBERADOR_SISTEMA, nativeQuery = true)
    void atualizarDataAuxLiberadorSistema();

}
