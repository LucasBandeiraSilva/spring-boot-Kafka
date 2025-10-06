package com.lucasbandeira.icompras.pedidos.model.service;

import com.lucasbandeira.icompras.pedidos.client.ServicoBancarioClient;
import com.lucasbandeira.icompras.pedidos.model.DadosPagamento;
import com.lucasbandeira.icompras.pedidos.model.Pedido;
import com.lucasbandeira.icompras.pedidos.model.enums.StatusPedido;
import com.lucasbandeira.icompras.pedidos.model.enums.TipoPagamento;
import com.lucasbandeira.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import com.lucasbandeira.icompras.pedidos.model.repository.ItemPedidoRepository;
import com.lucasbandeira.icompras.pedidos.model.repository.PedidoRepository;
import com.lucasbandeira.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioClient;

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

        if (sucesso){
            pedido.setStatus(StatusPedido.PAGO);
        }else{
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }
        repository.save(pedido);
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
}
