package com.lucasbandeira.icompras.pedidos.model.service;

import com.lucasbandeira.icompras.pedidos.client.ClientesClient;
import com.lucasbandeira.icompras.pedidos.client.ProdutosClient;
import com.lucasbandeira.icompras.pedidos.client.ServicoBancarioClient;
import com.lucasbandeira.icompras.pedidos.model.DadosPagamento;
import com.lucasbandeira.icompras.pedidos.model.ItemPedido;
import com.lucasbandeira.icompras.pedidos.model.Pedido;
import com.lucasbandeira.icompras.pedidos.model.enums.StatusPedido;
import com.lucasbandeira.icompras.pedidos.model.enums.TipoPagamento;
import com.lucasbandeira.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import com.lucasbandeira.icompras.pedidos.model.repository.ItemPedidoRepository;
import com.lucasbandeira.icompras.pedidos.model.repository.PedidoRepository;
import com.lucasbandeira.icompras.pedidos.publisher.PagamentoPublisher;
import com.lucasbandeira.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;
    private final ClientesClient apiClientes;
    private final ProdutosClient apiProdutos;
    private final PagamentoPublisher pagamentoPublisher;


    @Transactional
    public Pedido criarPedido(Pedido pedido){
            validator.validar(pedido);
            realizarPersistencia(pedido);
            enviarSolicitacaoPagamento(pedido);
            return pedido;

    }

    private void enviarSolicitacaoPagamento( Pedido pedido ) {
        String chavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia( Pedido pedido ) {
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    public void atualizarStatusPagamento( Long codigoPedido, String chavePagamento, boolean sucesso, String observacoes ) {
        var pedidoEncontrado = repository.findByCodigoAndChavePagamento(codigoPedido,chavePagamento);

        if (pedidoEncontrado.isEmpty()){
            var msg = String.format("Pedido não encontrado para o código %d e chave pagamento %s",codigoPedido,chavePagamento);
            log.error(msg);
            return;
        }
        Pedido pedido = pedidoEncontrado.get();

        if (sucesso) {
            prepararEPublicarPedidoPago(pedido);
        } else {
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }

        repository.save(pedido);
    }

    private void prepararEPublicarPedidoPago( Pedido pedido ) {
        pedido.setStatus(StatusPedido.PAGO);
        carregarDadosCliente(pedido);
        carregarItensPedido(pedido);
        pagamentoPublisher.publicar(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento( Long codigoPedido, String dadosCartao, TipoPagamento tipoPagamento ){
        var pedidoEcontrado = repository.findById(codigoPedido);

        if (pedidoEcontrado.isEmpty()) throw new ItemNaoEncontradoException("Pedido não encontrado para o código informado.");

        var pedido = pedidoEcontrado.get();

        var dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipoPagamento);
        dadosPagamento.setDados(dadosCartao);
        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando o novo processamento.");

        String novaChavePagamento = servicoBancarioClient.solicitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);

        repository.save(pedido);
    }

    public Optional<Pedido> carregarDadosCompletosPedidos(Long codigo){
        Optional<Pedido> pedido = repository.findById(codigo);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);
        return pedido;
    }

    private void carregarDadosCliente(Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        var response = apiClientes.obterPorCodigo(codigoCliente);
        pedido.setDadosCliente(response.getBody());
    }

    private void carregarItensPedido(Pedido pedido){
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        pedido.getItens().forEach(this::carregarDadosProduto);
    }

    private void carregarDadosProduto(ItemPedido itemPedido){
        Long codigoProduto = itemPedido.getCodigoProduto();
        var response = apiProdutos.obterPorCodigo(codigoProduto);
        itemPedido.setNome(response.getBody().nome());
    }
}
