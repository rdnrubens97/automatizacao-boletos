package com.alra.service.repository.cxempresa;

import com.alra.service.model.cxempresa.boletoxml.BoletoXml;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoletoXmlRepository extends JpaRepository<BoletoXml, String> {

    List<BoletoXml> findByEnviadoClienteFalse();
    @Query(value = ComandosSqlCxempresa.SELECIONAR_XML_AINDA_NAO_ENVIADO, nativeQuery = true)
    List<BoletoXml> selecionarXmlAindaNaoEnviado();

    @Modifying
    @Query(value = ComandosSqlCxempresa.ATUALIZAR_COLUNA_ENVIADO_CLIENTE, nativeQuery = true)
    void atualizarColunaEnviadoCliente(@Param("xml") String xml);
}
