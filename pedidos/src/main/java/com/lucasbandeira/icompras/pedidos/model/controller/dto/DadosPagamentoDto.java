package com.lucasbandeira.icompras.pedidos.model.controller.dto;

import com.lucasbandeira.icompras.pedidos.model.enums.TipoPagamento;

public record DadosPagamentoDto(String dados, TipoPagamento tipoPagamento) {
}
