package com.lucasbandeira.icompras.pedidos.model.controller.dto;

import java.util.List;

public record NovoPedidoDTO(Long codigoCliente, DadosPagamentoDto dadosPagamento, List<ItemPedidoDTO>itens){
}
