package com.alra.service.repository.cx230;

import com.alra.service.model.cx230.KeySistema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface KeySistemaRepository extends JpaRepository<KeySistema, Integer> {
    KeySistema findByIdSistema(String idSistema);
    @Transactional
    @Modifying
    @Query("UPDATE KeySistema k SET k.marketingCertificado = CURRENT_TIMESTAMP + 45 WHERE k.idSistema = :idSistema")
    void updateKeySistemaCertificadoByIdSistema(String idSistema);

    @Transactional
    @Modifying
    @Query(value = ComandosSqlCx230.SELECIONAR_DADOS_KEY_SISTEMA_PARA_MARKETING_BOLETO, nativeQuery = true)
    void selecionarDadosKeySistemaParaMarketingBoleto(@Param("id") String id);

}
