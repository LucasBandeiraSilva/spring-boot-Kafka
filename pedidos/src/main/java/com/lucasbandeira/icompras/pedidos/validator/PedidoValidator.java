package com.lucasbandeira.icompras.pedidos.validator;

import com.lucasbandeira.icompras.pedidos.client.ClientesClient;
import com.lucasbandeira.icompras.pedidos.client.ProdutosClient;
import com.lucasbandeira.icompras.pedidos.client.representation.ClienteRepresentation;
import com.lucasbandeira.icompras.pedidos.client.representation.ProdutoRepresentation;
import com.lucasbandeira.icompras.pedidos.model.ItemPedido;
import com.lucasbandeira.icompras.pedidos.model.Pedido;
import com.lucasbandeira.icompras.pedidos.model.exception.ValidationException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PedidoValidator {

    private final ProdutosClient produtosClient;
    private final ClientesClient clientesClient;

    public void validar( Pedido pedido){
        Long codigoCliente = pedido.getCodigoCliente();
        validarCliente(codigoCliente);
        pedido.getItens().forEach(this::validarItem);
    }

    private void validarCliente(Long codigoCliente){
        try {
            var response = clientesClient.obterPorCodigo(codigoCliente);
            ClienteRepresentation cliente = response.getBody();
            log.info("cliente encontrado: {}, nome: {}",cliente.codigo(),cliente.nome());
        } catch (FeignException.NotFound e) {
            var message = String.format("Cliente de código %d não encontrado",codigoCliente);
            throw new ValidationException("codigoCliente",message);
        }


    }

    private void validarItem( ItemPedido item ){
        try{
            var response = produtosClient.obterPorCodigo(item.getCodigoProduto());
            ProdutoRepresentation produto = response.getBody();
            log.info("produto encontrado: {} produto: {}",produto.codigo(),produto.nome());
        }catch (FeignException.NotFound e){
            var message = String.format("Produto de código %d não encontrado",item.getCodigoProduto());
            throw new ValidationException("codigoProduto",message);
        }
    }
}
