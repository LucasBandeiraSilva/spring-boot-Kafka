package com.lucasbandeira.icompras.pedidos.subscriber.representation;

import com.lucasbandeira.icompras.pedidos.model.enums.StatusPedido;

public record AtualizacaoStatusPedidoRepresentation(Long codigo, StatusPedido statusPedido, String urlNotaFiscal,String codigoRastreio) {
}
