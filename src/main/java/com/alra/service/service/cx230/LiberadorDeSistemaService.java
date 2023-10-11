package com.alra.service.service.cx230;

import com.alra.service.model.cx230.Estoque;
import com.alra.service.model.cx230.LiberadorDeSistema;
import com.alra.service.model.cx230.Pedido;
import com.alra.service.model.cxempresa.cliente.Cliente;
import com.alra.service.model.cxempresa.retornoconsultaboleto.Content;
import com.alra.service.repository.cx230.EstoqueRepository;
import com.alra.service.repository.cx230.LiberadorDeSistemaRepository;
import com.alra.service.repository.cx230.PedidoRepository;
import com.alra.service.repository.cxempresa.BoletoDtoRepository;
import com.alra.service.repository.cxempresa.ClienteRepository;
import com.alra.service.service.cxempresa.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class LiberadorDeSistemaService {
    @Autowired
    VendedorSistemaService vendedorSistemaService;
    @Autowired
    LiberadorDeSistemaRepository liberadorDeSistemaRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    BoletoDtoRepository boletoDtoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EstoqueRepository estoqueRepository;
    @Autowired
    private LogService logService;


    public void atualizacoesSistemaBoletoPago(LiberadorDeSistema liberadorDeSistema) {
        insereLiberacao(liberadorDeSistema);
        confirmaSeIdSistemaConstaNaTblCliente(liberadorDeSistema);
        vendedorSistemaService.insereComissao(liberadorDeSistema);
        inserePedidoEstoque(liberadorDeSistema);
        liberadorDeSistemaRepository.deleteById(liberadorDeSistema.getIdSistema());
    }

    /**
     * No banco de dados cx230, atualiza a tabela 'tbl_keysistema' atualizando a coluna 'pendente' como '1', 'liberou' como o momento atual, 'codmanual' como 'codigoManual',
     * 'pagouagora' como '0' onde a coluna 'id_sistema' tiver o valor de 'idSistema'
     * No banco de dados cxEmpresa, atualiza a 'tbl_cliente' passando o 'codigoAutomatico' na coluna 'liberacao' onde a coluna 'chave' tiver o valor de 'idSistema'
     *
     * @param liberadorDeSistema
     */
    public void insereLiberacao(LiberadorDeSistema liberadorDeSistema) {

        try {
            liberadorDeSistemaRepository.atualizarTabelaKeySistemaEmLiberacao(liberadorDeSistema.getIdSistema(), liberadorDeSistema.getCodigoManual());
            boletoDtoRepository.insereLiberacaoTblCliente(liberadorDeSistema.getIdSistema(), liberadorDeSistema.getCodigoAutomatico());
            logService.gerarLog(liberadorDeSistema.getIdSistema(), "Sucesso ao liberar sistema id cliente: " + liberadorDeSistema.getIdCliente(), "SUCESSO LIBERAÇÂO SISTEMA");

        } catch (Exception e) {
            logService.gerarLog(liberadorDeSistema.getIdSistema(), "Falha ao liberar sistema id cliente: " + liberadorDeSistema.getIdCliente(), "FALHA LIBERAÇÂO SISTEMA");

        }

    }

    /**
     * Verifica se o ID do sistema já existe na tabela "tbl_cliente" e cria um novo registro se não existir.
     *
     * @param liberadorDeSistema de onde buscamos o valor de idSistema
     */
    private void confirmaSeIdSistemaConstaNaTblCliente(LiberadorDeSistema liberadorDeSistema) {
        String idSistema = liberadorDeSistema.getIdSistema();
        try {
            Cliente cliente = Optional.of(clienteRepository.findByChave(idSistema)).orElse(null);
            if (cliente == null) {
                Cliente novoCliente = new Cliente();
                novoCliente.setChave(idSistema);
                clienteRepository.save(novoCliente);
                Cliente clienteCriado = clienteRepository.findByChave(idSistema);
                if (clienteCriado == null) {
                    throw new SQLException("Id cliente não criado");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insere um novo pedido e o respectivo registro de estoque no sistema.
     *
     * @param liberadorDeSistema
     */
    private void inserePedidoEstoque(LiberadorDeSistema liberadorDeSistema) {
        if (liberadorDeSistema.getValorPago() != null) {
            try {
                BigDecimal valorPago = new BigDecimal(liberadorDeSistema.getValorPago());
                if (!valorPago.equals(BigDecimal.ZERO)) {
                    Pedido pedido = new Pedido();
                    Estoque estoque = new Estoque();

                    int idCliente = Integer.parseInt(liberadorDeSistema.getIdCliente());
                    pedido.setStatus(2);
                    pedido.setIdCliente(Integer.parseInt(liberadorDeSistema.getIdCliente()));
                    if (idCliente > 1000000 && idCliente < 2000000) {
                        pedido.setIdClientePf(idCliente);
                    } else {
                        pedido.setIdClientePj(idCliente);
                    }
                    pedido.setValorLiquido(valorPago);
                    pedido.setAcrescimo(BigDecimal.ZERO);
                    pedido.setDesconto(BigDecimal.ZERO);
                    pedido.setIdFuncionario(1);
                    Pedido pedidoSalvo = pedidoRepository.save(pedido);

                    estoque.setIdProduto(liberadorDeSistema.getProduto());
                    estoque.setFuncao(2);
                    estoque.setQuantidade(BigDecimal.ONE);
                    estoque.setPreco(valorPago);
                    estoque.setIdPedido(pedidoSalvo.getIdPedido());
                    estoque.setIdFuncao(1);
                    estoqueRepository.save(estoque);
                }
            } catch (Exception e) {
                logService.gerarLog(liberadorDeSistema.getIdSistema(), "Erro ao inserir pedido para ID cliente: " + liberadorDeSistema.getIdCliente() + ". " + e.getMessage(), "ERRO AO INSERIR PEDIDO");
            }
        }
    }


    public void popularTblLiberadorDeSistema(List<Content> listaContent) {
        liberadorDeSistemaRepository.deleteAll();
        for (Content content : listaContent) {
            liberadorDeSistemaRepository.popularTblLiberadorDeSistema(content.getSeuNumero());
            List<LiberadorDeSistema> listaLiberadorSistema = liberadorDeSistemaRepository.findAll();
            for (LiberadorDeSistema liberadorDeSistema : listaLiberadorSistema) {
                liberadorDeSistemaRepository.atualizarParametros(liberadorDeSistema.getIdSistema());
                liberadorDeSistemaRepository.atualizarLiberadorSistemaComContent(liberadorDeSistema.getIdSistema(), liberadorDeSistema.getValorPago());
            }
        }
    }

    public LiberadorDeSistema buscarLiberadorDeSistemaPorIdSistema(String idSistema) {
        return liberadorDeSistemaRepository.findById(idSistema).orElse(null);
    }

    public List<LiberadorDeSistema> listarLiberadorSistema() {
        List<LiberadorDeSistema> listaLiberadorSistema = liberadorDeSistemaRepository.findAll();
        return listaLiberadorSistema;
    }

}
