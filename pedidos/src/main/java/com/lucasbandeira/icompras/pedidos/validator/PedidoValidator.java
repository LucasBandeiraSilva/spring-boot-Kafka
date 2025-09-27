package com.lucasbandeira.icompras.pedidos.validator;

import com.lucasbandeira.icompras.pedidos.client.ClientesClient;
import com.lucasbandeira.icompras.pedidos.client.ProdutosClient;
import com.lucasbandeira.icompras.pedidos.client.representation.ProdutoRepresentation;
import com.lucasbandeira.icompras.pedidos.model.ItemPedido;
import com.lucasbandeira.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar( Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente){}

    private void validarItem( ItemPedido item ){}
}
