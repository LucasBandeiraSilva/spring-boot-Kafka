package com.lucasbandeira.icompras.pedidos.model.controller.dto;

import java.math.BigDecimal;

public record ItemPedidoDTO(Long codigoProduto, Integer quantidade, BigDecimal valorUnitario) {
}
