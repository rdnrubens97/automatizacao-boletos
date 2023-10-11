package com.alra.service.repository.cxempresa;

import com.alra.service.model.cxempresa.boletosistema.BoletoSistema;
import com.alra.service.model.cxempresa.retornoconsultaboleto.Content;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Repository
public interface BoletoSistemaRepository extends JpaRepository<BoletoSistema, Integer> {

    @Transactional
    @Modifying
    @Query("""
            UPDATE BoletoSistema b SET b.liberacao = 1, 
                            b.pagamentoStatus = 1, 
                            b.dataPagamento = :dataHoraSituacao, 
                            b.valorPago = :valorTotalRecebimento, 
                            b.taxa = :taxaCalculada 
                            WHERE b.codigo = :nossoNumero AND b.pagamentoStatus <> 1
            """)
    void atualizarTblBoletoParaBoletosPagos(
            @Param("dataHoraSituacao") Date dataHoraSituacao,
            @Param("valorTotalRecebimento") BigDecimal valorTotalRecebimento,
            @Param("taxaCalculada") BigDecimal taxaCalculada,
            @Param("nossoNumero") String nossoNumero
    );

    default void atualizarDadosBoletosPagosTblBoleto(Content content) {
        if (content != null) {
            atualizarTblBoletoParaBoletosPagos(
                    content.getDataHoraSituacaoAsDate(),
                    content.getValorTotalRecebimento(),
                    content.getValorTotalRecebimento().subtract(content.getValorNominal()),
                    content.getNossoNumero()
            );
        }
    }

    @Query("SELECT b.codigo FROM BoletoSistema b WHERE b.pagamentoStatus <> 1 AND b.codigo = :nossoNumero")
    String findCodigoWithStatusNot1AndCodigoEquals(@Param("nossoNumero") String nossoNumero);

    @Transactional
    @Modifying
    @Query("UPDATE BoletoSistema b SET b.pagamentoStatus = 0 WHERE b.codigo = :codigo")
    void setarPagoStatusComoZero(@Param("codigo") String codigo);

    @Query(value = "SELECT * FROM tbl_boleto " +
            "WHERE vencimento > current_date " +
            "  AND vencimento < (current_date + interval '5 days') " +
            "  AND (enviowhatsapp + interval '10 days') < current_date " +
            "  AND status = 1 " +
            "  AND pagostatus = 0", nativeQuery = true)
    List<BoletoSistema> selecionarBoletosParaEnvioMarketingBoleto();

    @Query(value = "SELECT * FROM tbl_boleto " +
            "WHERE vencimento > current_date " +
            "  AND vencimento < (current_date + interval '5 days') " +
            "  AND (enviowhatsapp + interval '10 days') < current_date " +
            "  AND status = 1 " +
            "  AND pagostatus = 0", nativeQuery = true)
    List<BoletoSistema> selecionarBoletosParaEnvioMarketingBoleto(@Param("startDate") LocalDate startDate,
                                                                  @Param("endDate") LocalDate endDate,
                                                                  @Param("interval") Period interval);

    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_boleto SET enviowhatsapp = now() WHERE id_sistema = :idSistema", nativeQuery = true)
    void atualizarEnvioWhatsApp(@Param("idSistema") String idSistema);


}
