package com.lucasbandeira.icompras.pedidos.model.service;

import com.lucasbandeira.icompras.pedidos.model.Pedido;
import com.lucasbandeira.icompras.pedidos.model.repository.ItemPedidoRepository;
import com.lucasbandeira.icompras.pedidos.model.repository.PedidoRepository;
import com.lucasbandeira.icompras.pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;

    public Pedido criarPedido(Pedido pedido){
        repository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
        return pedido;
    }
}
