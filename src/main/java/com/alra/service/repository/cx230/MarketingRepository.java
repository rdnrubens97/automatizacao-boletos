package com.alra.service.repository.cx230;

import com.alra.service.model.cx230.Marketing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketingRepository extends JpaRepository<Marketing, String> {
    @Modifying
    @Query(value = ComandosSqlCx230.POPULAR_AUX_MARKETING_PARA_CERTIFICADO, nativeQuery = true)
    void popularAuxMarketingParaCertificado();

    @Modifying
    @Query(value = ComandosSqlCx230.POPULAR_AUX_MARKETING_PARA_BOLETO, nativeQuery = true)
    void popularAuxMarketingParaBoleto(@Param("idSistema") String idSistema);

    @Query(value = "SELECT email FROM tbl_clientepf WHERE cpf = :cpf", nativeQuery = true)
    String selecionarEmailPessoaFisica(@Param("cpf") String cpf);

    @Query(value = "SELECT email FROM tbl_clientepj WHERE cnpj = :cnpj", nativeQuery = true)
    String selecionarEmailPessoaJuridica(@Param("cnpj") String cnpj);


}
