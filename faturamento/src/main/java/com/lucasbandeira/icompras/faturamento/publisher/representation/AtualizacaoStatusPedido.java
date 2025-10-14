package com.lucasbandeira.icompras.faturamento.publisher.representation;

public record AtualizacaoStatusPedido(Long codigo, StatusPedido statusPedido, String urlNotaFiscal) {
}
