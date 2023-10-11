package com.alra.service.repository.cx230;

public class ComandosSqlCx230 {

    public static final String ATUALIZAR_TBL_GERACAO_BOLETO = """      
            DELETE FROM aux_geracao_boleto;
                        
            INSERT INTO aux_geracao_boleto (
                meses,
                dia,
                emissaoboleto,
                count,
                rua,
                numero,
                complemento,
                bairro,
                cidade,
                cep,
                uf,
                tel1,
                email,
                nomecliente,
                cpfcliente,
                id_sistema,
                validade,
                versao,
                ultimoacesso,
                pendente,
                liberou,
                premium,
                pc,
                valor_sistema,
                codmanual,
                id_cliente,
                pagouagora,
                valorpago,
                key,
                tempoempresa,
                nome_vendedor,
                id_vendedor,
                comissao,
                descricao
            )
            SELECT
                ks.meses,
                ks.dia,
                DATE(ks.emissaoboleto) AS emissaoboleto,
                b.count,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.logadouro
                    ELSE cpj.logadouro
                END AS rua,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.numero
                    ELSE cpj.numero
                END AS numero,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.complemento
                    ELSE cpj.complemento
                END AS complemento,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.bairro
                    ELSE cpj.bairro
                END AS bairro,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.cidade
                    ELSE cpj.cidade
                END AS cidade,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.cep
                    ELSE cpj.cep
                END AS cep,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.uf
                    ELSE cpj.uf
                END AS uf,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.tel1
                    ELSE cpj.tel1
                END AS tel1,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.email
                    ELSE cpj.email
                END AS email,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.nome
                    ELSE cpj.fantasia
                END AS nomecliente,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.cpf
                    ELSE cpj.cnpj
                END AS cpfcliente,
                ks.id_sistema,
                ks.validade,
                ks.versao,
                ks.ultimoacesso,
                ks.pendente,
                ks.liberou,
                CASE
                            WHEN ks.premium = 1 THEN 'PREMIUM'
                            ELSE 'PRO'
                        END AS premium,
                ks.pc,
                ks.valor AS valor_sistema,
                ks.codmanual,
                ks.id_cliente,
                ks.pagouagora,
                ks.valorpago,
                ks.key,
                ks.tempoempresa,
                inf_vendedor.nome AS nome_vendedor,
                ks.id_vendedor,
                ks.comissao,           
                ''
            FROM
                tbl_keysistema ks
            LEFT JOIN
                inf_segmento ON inf_segmento.id_segmento = ks.segmento
            LEFT JOIN
                inf_vendedor ON inf_vendedor.id_vendedor = ks.id_vendedor
            LEFT JOIN (
                SELECT id_cliente, COUNT(*) AS count
                FROM tbl_keysistema
                WHERE status = 1
                GROUP BY id_cliente
            ) b ON b.id_cliente = ks.id_cliente
            LEFT JOIN
                tbl_clientepf cpf ON CAST(ks.id_cliente AS INTEGER) < 2000000 AND cpf.id_clientepf = CAST(ks.id_cliente AS INTEGER)
            LEFT JOIN
                tbl_clientepj cpj ON CAST(ks.id_cliente AS INTEGER) >= 2000000 AND cpj.id_clientepj = CAST(ks.id_cliente AS INTEGER)
            WHERE
                ks.status = 1
                AND ks.valor <> 0
                AND (ks.meses = 0 OR ks.meses = 1)
                AND ks.emissaoboleto + 15 < CURRENT_DATE;
                
            UPDATE aux_geracao_boleto SET descricao = CONCAT('Licença Sistema versão ',
                                               premium,
                                               CASE WHEN pc <> 1 THEN CONCAT(' ', pc, ' PCs') ELSE '' END,
                                               CASE WHEN count > 1 THEN CONCAT(' ', count, ' LOJAS') ELSE '' END,
                                               ' ID_SISTEMA: ', id_sistema);
                                               
            UPDATE aux_geracao_boleto
            SET vencimento =
                CASE
                    WHEN
                        EXTRACT(MONTH FROM emissaoboleto) = 1 AND
                        EXTRACT(DAY FROM emissaoboleto) >= 29
                    THEN
                        DATE_TRUNC('MONTH', emissaoboleto) + INTERVAL '1 MONTH' - INTERVAL '1 DAY'
                    ELSE
                        DATE_TRUNC('MONTH', emissaoboleto) + INTERVAL '1 MONTH' + (EXTRACT(DAY FROM emissaoboleto) - 1) * INTERVAL '1 DAY'
                END; 
                
            UPDATE aux_geracao_boleto
            SET tipo_pessoa = CASE
                WHEN LENGTH(cpfcliente) = 11 THEN 'FISICA'
                ELSE 'JURIDICA'
            END;
                                  
            """;

    public static final String ATUALIZAR_COLUNA_EMISSAOBOLETO_TBL_KEY_SISTEMA = """
            UPDATE tbl_keysistema SET emissaoboleto = :dataVencimento WHERE id_sistema = :seuNumero                   
            """;

    public static final String POPULAR_TBL_LIBERADOR_DE_SISTEMA = """            
            INSERT INTO public.aux_liberador_de_sistema (
                id_sistema,
                cod_manual,
                comissao,
                cpf_cliente,
                descricao,
                dia,
                emissao_boleto,
                id_cliente,
                id_vendedor,
                key,
                liberou,
                marketing,
                meses,
                nome_cliente,
                nome_vendedor,
                pagou_agora,
                pc,
                pendente,
                produto,
                segmento_obs,
                segname,
                tel1,
                tel2,
                tempo_empresa,
                ultimo_acesso,
                validade,
                valor,
                valor_pago,
                versao
            )
            SELECT
                ks.id_sistema,
                ks.codmanual,
                ks.comissao,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.cpf
                    ELSE cpj.cnpj
                END AS cpf_cliente,
                ks.descricao,
                LPAD(CAST(ks.dia AS TEXT), 2, '0') AS dia,
                ks.emissaoboleto,
                ks.id_cliente,
                ks.id_vendedor,
                ks.key,
                ks.liberou,
                ks.marketing,
                ks.meses,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN cpf.nome
                    ELSE cpj.fantasia
                END AS nome_cliente,
                v.nome AS nome_vendedor,
                ks.pagouagora,
                ks.pc,
                ks.pendente,
                CASE
                    WHEN ks.premium = 0 THEN 1
                    ELSE 2
                    END AS produto,
                ks.segmentoobs,
                ks.segmento AS segname,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN CAST(cpf.tel1 AS text)
                    ELSE CAST(cpj.tel1 AS text)
                END AS tel1,
                CASE
                    WHEN CAST(ks.id_cliente AS INTEGER) < 2000000 THEN CAST(cpf.tel2 AS text)
                    ELSE CAST(cpj.tel2 AS text)
                END AS tel2,
                ks.tempoempresa,
                ks.ultimoacesso,
                ks.validade,
                ks.valor,
                ks.valorpago,
                ks.versao
            FROM
                tbl_keysistema ks
            LEFT JOIN
                tbl_clientepf cpf ON cpf.id_clientepf = CAST(ks.id_cliente AS INTEGER)
            LEFT JOIN
                tbl_clientepj cpj ON cpj.id_clientepj = CAST(ks.id_cliente AS INTEGER)
            LEFT JOIN
                inf_vendedor v ON v.id_vendedor = ks.id_vendedor
            WHERE
                ks.id_cliente = (SELECT id_cliente FROM tbl_keysistema WHERE id_sistema = :idSistemaPrincipal)
                AND ks.status = 1
            ON CONFLICT (id_sistema) DO UPDATE
            SET
                cod_manual = EXCLUDED.cod_manual,
                comissao = EXCLUDED.comissao,
                cpf_cliente = EXCLUDED.cpf_cliente,
                descricao = EXCLUDED.descricao,
                dia = EXCLUDED.dia,
                emissao_boleto = EXCLUDED.emissao_boleto,
                id_cliente = EXCLUDED.id_cliente,
                id_vendedor = EXCLUDED.id_vendedor,
                "key" = EXCLUDED."key",
                liberou = EXCLUDED.liberou,
                marketing = EXCLUDED.marketing,
                meses = EXCLUDED.meses,
                nome_cliente = EXCLUDED.nome_cliente,
                nome_vendedor = EXCLUDED.nome_vendedor,
                pagou_agora = EXCLUDED.pagou_agora,
                pc = EXCLUDED.pc,
                pendente = EXCLUDED.pendente,
                produto = EXCLUDED.produto,
                segmento_obs = EXCLUDED.segmento_obs,
                segname = EXCLUDED.segname,
                tel1 = EXCLUDED.tel1,
                tel2 = EXCLUDED.tel2,
                tempo_empresa = EXCLUDED.tempo_empresa,
                ultimo_acesso = EXCLUDED.ultimo_acesso,
                validade = EXCLUDED.validade,
                valor = EXCLUDED.valor,
                valor_pago = EXCLUDED.valor_pago,
                versao = EXCLUDED.versao;             
                                      
            """;

    public static final String ATUALIZAR_TABELA_KEY_SISTEMA_EM_LIBERACAO = """
            UPDATE tbl_keysistema SET pendente = 1, liberou = CURRENT_TIMESTAMP, codmanual = :codigoManual, pagouagora = 0 WHERE id_sistema = :idSistema
            """;

    public static final String POPULAR_AUX_MARKETING_PARA_CERTIFICADO = """
            DELETE FROM aux_marketing;
            INSERT INTO aux_marketing (
                certificado,
                id_cliente,
                tel1,
                tel2,
                nome_cliente,
                cpf_cliente,
                id_sistema
            )
            SELECT
                ks.certificado,
                ks.id_cliente,
                CASE
                    WHEN ks.id_cliente::integer < 2000000 THEN pf.tel1
                    ELSE pj.tel1
                    END AS tel1,
                CASE
                    WHEN ks.id_cliente::integer < 2000000 THEN pf.tel2
                    ELSE pj.tel2
                    END AS tel2,
                CASE
                    WHEN ks.id_cliente::integer < 2000000 THEN pf.nome
                    ELSE pj.fantasia
                    END AS nome_cliente,
                CASE
                    WHEN ks.id_cliente::integer < 2000000 THEN pf.cpf
                    ELSE pj.cnpj
                    END AS cpf_cliente,
                    ks.id_sistema
                FROM tbl_keysistema ks
                    LEFT JOIN tbl_clientepf pf ON ks.id_cliente::integer < 2000000 AND pf.id_clientepf = ks.id_cliente::integer
                    LEFT JOIN tbl_clientepj pj ON ks.id_cliente::integer >= 2000000 AND pj.id_clientepj = ks.id_cliente::integer
                WHERE ks.status = 1
                    AND ks.certificado > NOW()
                    AND ks.certificado < (NOW() + INTERVAL '15 days')
                    AND ks.marketingcertificado < NOW();          
            """;

    public static final String POPULAR_AUX_MARKETING_PARA_BOLETO = """
            DELETE FROM aux_marketing;
            INSERT INTO aux_marketing (
            	id_sistema,
            	id_cliente,
            	nome_cliente,
            	cpf_cliente,
            	tel1,
            	tel2,
            	certificado
            )
            SELECT
                k.id_cliente,
                CASE
                    WHEN CAST(k.id_cliente AS integer) < 2000000 THEN pf.tel1
                    ELSE pj.tel1
                END AS tel1,
                CASE
                    WHEN CAST(k.id_cliente AS integer) < 2000000 THEN pf.tel2
                    ELSE pj.tel2
                END AS tel2,
                CASE
                    WHEN CAST(k.id_cliente AS integer) < 2000000 THEN pf.nome
                    ELSE pj.fantasia
                END AS nome_cliente,
                CASE
                    WHEN CAST(k.id_cliente AS integer) < 2000000 THEN pf.cpf
                    ELSE pj.cnpj
                END AS cpf_cliente,
                k.id_sistema
            FROM tbl_keysistema k
            LEFT JOIN tbl_clientepf pf ON CAST(k.id_cliente AS integer) < 2000000 AND pf.id_clientepf = CAST(k.id_cliente AS integer)
            LEFT JOIN tbl_clientepj pj ON CAST(k.id_cliente AS integer) >= 2000000 AND pj.id_clientepj = CAST(k.id_cliente AS integer)
            WHERE k.id_sistema = :idSistema AND k.enviar = 1                        
            """;

    public static final String ATUALIZAR_DATA_AUX_LIBERADOR_SISTEMA = """
            UPDATE aux_liberador_de_sistema AS a
                SET data = b.data
                FROM aux_liberador_de_sistema AS b
            WHERE a.data IS NULL
                AND a.id_cliente = b.id_cliente
                AND b.data IS NOT NULL;                 
            """;

    public static final String SELECIONAR_DADOS_KEY_SISTEMA_PARA_MARKETING_BOLETO = """
            delete from aux_marketing_boleto_cx230;
            INSERT INTO aux_marketing_boleto_cx230 (
                id_cliente,
                tel1,
                tel2,
                nome_cliente,
                cpf_cliente,
                id_sistema
            )
            SELECT
                id_cliente,
                CASE
                    WHEN CAST(CAST(id_cliente AS TEXT) AS INTEGER) < 2000000
                    THEN (SELECT tel1 FROM tbl_clientepf WHERE tbl_clientepf.id_clientepf = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                    ELSE (SELECT tel1 FROM tbl_clientepj WHERE tbl_clientepj.id_clientepj = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                END AS tel1,
                CASE
                    WHEN CAST(CAST(id_cliente AS TEXT) AS INTEGER) < 2000000
                    THEN (SELECT tel2 FROM tbl_clientepf WHERE tbl_clientepf.id_clientepf = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                    ELSE (SELECT tel2 FROM tbl_clientepj WHERE tbl_clientepj.id_clientepj = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                END AS tel2,
                CASE
                    WHEN CAST(CAST(id_cliente AS TEXT) AS INTEGER) < 2000000
                    THEN (SELECT nome FROM tbl_clientepf WHERE tbl_clientepf.id_clientepf = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                    ELSE (SELECT fantasia FROM tbl_clientepj WHERE tbl_clientepj.id_clientepj = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                END AS nome_cliente,
                CASE
                    WHEN CAST(CAST(id_cliente AS TEXT) AS INTEGER) < 2000000
                    THEN (SELECT cpf FROM tbl_clientepf WHERE tbl_clientepf.id_clientepf = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                    ELSE (SELECT cnpj FROM tbl_clientepj WHERE tbl_clientepj.id_clientepj = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                END AS cpf_cliente,
                id_sistema
            FROM tbl_keysistema
            WHERE id_sistema = :id
                AND enviar = 1
                AND EXISTS (
                    SELECT 1
                    FROM tbl_keysistema
                    WHERE id_sistema = :id
                        AND enviar = 1
            );
            """;

//    public static final String SELECIONAR_DADOS_KEY_SISTEMA_PARA_MARKETING_BOLETO = """
//            delete from aux_marketing_boleto_cx230;
//            insert into aux_marketing_boleto_cx230 (
//            	id_cliente,
//            	tel1,
//            	tel2,
//            	nome_cliente,
//            	cpf_cliente,
//            	id_sistema
//            )
//            select id_cliente,
//                case when cast(cast(id_cliente as text) as integer) < 2000000
//                then (select tel1 from tbl_clientepf where tbl_clientepf.id_clientepf = cast(cast(id_cliente as text) as integer))
//                else (select tel1 from tbl_clientepj where tbl_clientepj.id_clientepj = cast(cast(id_cliente as text) as integer))
//            end as tel1,
//                case when cast(cast(id_cliente as text) as integer) < 2000000
//                then (select tel2 from tbl_clientepf where tbl_clientepf.id_clientepf = cast(cast(id_cliente as text) as integer))
//                else (select tel2 from tbl_clientepj where tbl_clientepj.id_clientepj = cast(cast(id_cliente as text) as integer))
//            end as tel2,
//                case when cast(cast(id_cliente as text) as integer) < 2000000
//                then (select nome from tbl_clientepf where tbl_clientepf.id_clientepf = cast(cast(id_cliente as text) as integer))
//                else (select fantasia from tbl_clientepj where tbl_clientepj.id_clientepj = cast(cast(id_cliente as text) as integer))
//            end as nome_cliente,
//                case when cast(cast(id_cliente as text) as integer) < 2000000
//                then (select cpf from tbl_clientepf where tbl_clientepf.id_clientepf = cast(cast(id_cliente as text) as integer))
//                else (select cnpj from tbl_clientepj where tbl_clientepj.id_clientepj = cast(cast(id_cliente as text) as integer))
//                end as cpf_cliente,
//                id_sistema
//            from tbl_keysistema where id_sistema = :id and enviar = 1
//            """;

    public static final String SELECIONAR_DADOS_ENVIO_CHAVE_MARKETING = """
            DELETE FROM aux_marketing_envio_chave;
            INSERT INTO aux_marketing_envio_chave (id_sistema, codmanual, tel1)
            SELECT
                id_sistema,
                codmanual,
                CASE
                    WHEN CAST(CAST(id_cliente AS TEXT) AS INTEGER) < 2000000 THEN
                        (SELECT tel1 FROM tbl_clientepf WHERE tbl_clientepf.id_clientepf = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                    ELSE
                        (SELECT tel1 FROM tbl_clientepj WHERE tbl_clientepj.id_clientepj = CAST(CAST(id_cliente AS TEXT) AS INTEGER))
                END AS tel1
            FROM
                tbl_keysistema
            WHERE
                status = 1
                AND validade < NOW()
                AND liberou > (NOW() - INTERVAL '15 DAY')
                AND ultimoacesso < (NOW() - INTERVAL '3 DAY')
                AND pendente = 1;                        
            """;

}