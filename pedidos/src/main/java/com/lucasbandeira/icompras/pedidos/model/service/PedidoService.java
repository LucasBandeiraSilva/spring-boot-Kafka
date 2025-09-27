package com.lucasbandeira.icompras.pedidos.model.service;

import com.lucasbandeira.icompras.pedidos.client.ServicoBancarioClient;
import com.lucasbandeira.icompras.pedidos.model.Pedido;
import com.lucasbandeira.icompras.pedidos.model.exception.ValidationException;
import com.lucasbandeira.icompras.pedidos.model.repository.ItemPedidoRepository;
import com.lucasbandeira.icompras.pedidos.model.repository.PedidoRepository;
import com.lucasbandeira.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
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
}
