package com.alra.service.repository.cxempresa;

public class ComandosSqlCxempresa {

    public static final String ATUALIZAR_TBL_BOLETO_PARA_BOLETOS_PAGOS = """
            UPDATE tbl_boleto
                SET liberacao = 1,
                    pagostatus = 1,
                    datapagamento = :dataHoraSituacao,
                    valorpago = :valorTotalRecebimento,
                    taxa = :taxaCalculada
                WHERE codigo = :nossoNumero
                AND pagostatus <> 1
            """;

    public static final String RETORNAR_ID_SISTEMA_COM_NOSSO_NUMERO = """
            SELECT id_sistema FROM tbl_boleto WHERE codigo = :nossoNumero
            """;

    public static final String SINCRONIZAR_TBL_BOLETO_COM_TBL_BOLETO_REGISTRADO = """
            DELETE FROM aux_boleto_dto
            WHERE seu_numero IN (
                SELECT seu_numero
                FROM aux_boleto_dto
                WHERE seu_numero IN (SELECT seu_numero FROM aux_boleto_registrado)
            );
            """;

    public static final String INSERE_LIBERACAO_TBL_CLIENTE = """
            UPDATE tbl_cliente SET liberacao = :codigoAutomatico WHERE chave = :idSistema
            """;

    public static final String SELECIONAR_BOLETOS_REGISTRADOS_PARA_GERAR_XML = """
            SELECT * FROM aux_boleto_registrado WHERE gerado_xml = false;                        
            """;

    public static final String ATUALIZAR_COLUNA_GERADO_XML = """
            UPDATE aux_boleto_registrado SET gerado_xml = true WHERE codigo_barras = :codigoBarras;
            """;

    public static final String SELECIONAR_XML_AINDA_NAO_ENVIADO = """
            SELECT * FROM aux_boleto_xml WHERE enviado_cliente = false;                        
            """;

    public static final String ATUALIZAR_COLUNA_ENVIADO_CLIENTE = """
            UPDATE aux_boleto_xml SET enviado_cliente = true WHERE xml = :xml;
            """;



}
