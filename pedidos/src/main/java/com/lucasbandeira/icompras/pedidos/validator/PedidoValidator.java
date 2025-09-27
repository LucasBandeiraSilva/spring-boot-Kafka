package com.lucasbandeira.icompras.pedidos.validator;

import com.lucasbandeira.icompras.pedidos.client.ProdutosClient;
import com.lucasbandeira.icompras.pedidos.client.representation.ProdutoRepresentation;
import com.lucasbandeira.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private final ProdutosClient produtosClient;

    public void validar( Pedido pedido){
    }
}
