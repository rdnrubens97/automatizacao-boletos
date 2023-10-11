package com.alra.service.repository.cxempresa;

import com.alra.service.model.cxempresa.boletoregistrado.BoletoRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoletoRegistradoRepository extends JpaRepository<BoletoRegistrado, String> {

    List<BoletoRegistrado> findByGeradoXmlFalse();
    @Query(value = ComandosSqlCxempresa.SELECIONAR_BOLETOS_REGISTRADOS_PARA_GERAR_XML, nativeQuery = true)
    List<BoletoRegistrado> selecionarBoletosRegistradosParaGerarXml();

    @Modifying
    @Query(value = ComandosSqlCxempresa.ATUALIZAR_COLUNA_GERADO_XML, nativeQuery = true)
    void atualizarColunaGeradoXml(@Param("codigoBarras") String codigoBarras);
}
