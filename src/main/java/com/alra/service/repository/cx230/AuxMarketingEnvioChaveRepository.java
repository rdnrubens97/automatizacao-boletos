package com.alra.service.repository.cx230;

import com.alra.service.model.cx230.AuxMarketingEnvioChave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AuxMarketingEnvioChaveRepository extends JpaRepository<AuxMarketingEnvioChave, String> {
    @Transactional
    @Query(value = ComandosSqlCx230.SELECIONAR_DADOS_ENVIO_CHAVE_MARKETING, nativeQuery = true)
    List<AuxMarketingEnvioChave> selecionarDadosEnvioChaveMarketing();

    @Transactional
    @Modifying
    @Query(value = "UPDATE AuxMarketingEnvioChave SET pendente = 0 WHERE id_sistema = :idSistema", nativeQuery = true)
    void atualizarPendenteKeySistema(String idSistema);

}
