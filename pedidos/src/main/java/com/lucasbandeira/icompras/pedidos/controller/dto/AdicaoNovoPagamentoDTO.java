package com.lucasbandeira.icompras.pedidos.controller.dto;

import com.lucasbandeira.icompras.pedidos.model.enums.TipoPagamento;

public record AdicaoNovoPagamentoDTO(Long codigoPedido, String dadosCartao, TipoPagamento tipoPagamento) {
}
