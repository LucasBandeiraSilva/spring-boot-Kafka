package com.lucasbandeira.icompras.logistica.subscriber.representation;

import com.lucasbandeira.icompras.logistica.model.StatusPedido;

public record AtualizacaoFaturamentoRepresentation(Long codigo, StatusPedido statusPedido, String urlNotaFiscal) {
}
